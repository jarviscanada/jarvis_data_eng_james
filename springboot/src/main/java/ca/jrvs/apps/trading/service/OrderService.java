package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
                        QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /**
     * Execute a market order
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     */
    public SecurityOrder executeMarketOrder(MarketDataDto orderDto) {

        Integer accountId = orderDto.getAccountId();
        Integer size = orderDto.getSize();
        String ticker = orderDto.getTicker();

        if (accountId == null) {
            throw new IllegalArgumentException("Account ID is empty");
        }

        if (size == null) {
            throw new IllegalArgumentException("Size is empty");
        }

        if (ticker == null) {
            throw new IllegalArgumentException("Ticker is empty");
        }

        Quote quote = quoteDao.findById(ticker).get();
        if (quote == null) {
            throw new IllegalArgumentException("Invalid ticker");
        }

        Account account = accountDao.findById(accountId).get();
        if (account == null) {
            throw new IllegalArgumentException("Invalid Account ID");
        }

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setSize(size);
        securityOrder.setTicker(ticker);
        securityOrder.setAccountId(accountId);
        securityOrder.setStatus("CREATED");

        if (size > 0) {
            securityOrder.setPrice(quote.getAskPrice());
            handleBuyMarketOrder(orderDto,securityOrder,account);
        } else if (size < 0) {
            securityOrder.setPrice(quote.getBidPrice());
            handleSellMarketOrder(orderDto,securityOrder,account);
        } else {
            throw new IllegalArgumentException("Size can not be 0");
        }
        return securityOrder;
    }

    /**
     * Helper method that execute a buy order
     * @param marketDataDto user order
     * @param securityOrder to be saved in data database
     * @param account account
     */
    protected void handleBuyMarketOrder(MarketDataDto marketDataDto, SecurityOrder securityOrder,
                                        Account account) {
        Double amount = securityOrder.getPrice() * securityOrder.getSize();
        if (amount > account.getAmount()) {
            throw new IllegalArgumentException("Not enough fund in the account");
        }
        account.setAmount(account.getAmount() - amount);
        accountDao.save(account);
        securityOrder.setStatus("FILLED");
        securityOrder.setId(securityOrderDao.save(securityOrder).getId());

    }

    /**
     * Helper method that execute a sell order
     * @param marketDataDto user order
     * @param securityOrder to be saved in data database
     * @param account account
     */
    protected void handleSellMarketOrder(MarketDataDto marketDataDto, SecurityOrder securityOrder,
                                         Account account) {
        List<Position> positions = positionDao.findByIdList(account.getId());
        Position targetPosition = null;
        for (Position position : positions) {
            if (position.getAccountId() == marketDataDto.getAccountId()) {
                targetPosition = position;
            }
        }
        if (targetPosition == null) {
            new IllegalArgumentException("Do not won position for security");
        }

        if (targetPosition.getPosition() + securityOrder.getSize() >= 0) {
            Double amount = securityOrder.getPrice() * securityOrder.getSize() * -1;
            account.setAmount(account.getAmount() + amount);
            accountDao.save(account);
            securityOrder.setStatus("FILLED");
            securityOrder.setId(securityOrderDao.save(securityOrder).getId());
        }
    }
}
