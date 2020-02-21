package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.*;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DashboardService {

    private TraderDao traderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;
    private QuoteDao quoteDao;

    @Autowired
    public  DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao,
                             QuoteDao quoteDao) {
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.accountDao = accountDao;
        this.quoteDao = quoteDao;
    }

    /**
     * Create and return traderAccountView by Trader ID
     * @param traderId must be not null
     * @return
     */
    public TraderAccountView getTraderAccount(Integer traderId) {

        if (traderId == null) {
            new IllegalArgumentException("Invalid traderId");
        }
        Trader trader = traderDao.findById(traderId).get();
        Account account = findAccountByTraderId(traderId);
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setTrader(trader);
        traderAccountView.setAccount(account);
        return traderAccountView;
    }

    /**
     * Create and return portfolioView by Trader ID
     * @param traderId must be not null
     */
    public PortfolioView getPortfolioByTraderId(Integer traderId) {
        if (traderId == null) {
            new IllegalArgumentException("Invalid traderId");
        }

        Account account = findAccountByTraderId(traderId);
        List<Position> positions = positionDao.findByIdList(account.getId());

        List<SecurityRow> securityRows = new ArrayList<>();
        for (Position position : positions) {
            SecurityRow securityRow = new SecurityRow();
            securityRow.setPosition(position);
            securityRow.setTicker(position.getTicker());
            securityRow.setQuote(quoteDao.findById(position.getTicker()).get());
            securityRows.add(securityRow);
        }
        PortfolioView portfolioView = new PortfolioView();
        portfolioView.setSecurityRowList(securityRows);
        return portfolioView;
    }

    private Account findAccountByTraderId(Integer traderId) {
        Account account = accountDao.findByTraderId(traderId).get(0);
        if (account == null) {
            new IllegalArgumentException("Invalid traderId");
        }
        return account;
    }
}
