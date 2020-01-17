package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraderAccountService {

    private TraderDao traderDao;
    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private PositionDao positionDao;

    @Autowired
    public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
                                PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new Trader and initialize a new account with 0 amount
     * @param trader cannot be null. All fields cannot be null expected for id
     * @return traderAccountView
     */
    public TraderAccountView createTraderAndAccount(Trader trader) {

        if (trader.getId() != null || trader.getCountry() == null || trader.getDob() == null ||
            trader.getEmail() == null || trader.getFirstName() == null || trader.getLastName() == null) {
            throw new IllegalArgumentException("All trader's fields cannot be null expected for ID");
        }

        Trader savedTrader = traderDao.save(trader);
        Account account = new Account();
        account.setTraderId(savedTrader.getId());
        account.setAmount(0d);
        account.setId(accountDao.save(account).getId());

        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setAccount(account);
        traderAccountView.setTrader(savedTrader);

        return traderAccountView;
    }

    /**
     * A trader can be deleted iff it has no open position and 0 cash balance
     * @param traderId must not be null
     */
    public void deleteTraderById(Integer traderId) {
        if (traderId == null) {
            throw new IllegalArgumentException("Trader ID cannot be null");
        }

        Account account = accountDao.findByTraderId(traderId).get(0);
        if (account == null) {
            throw new IllegalArgumentException("Trader account cannot be found");
        }

        List<Position> positions = positionDao.findByIdList(account.getId());
        for (Position position : positions) {
            if (position.getPosition() != 0) {
                throw new IllegalArgumentException("Still have open position");
            }
        }

        if (account.getAmount() != 0) {
            throw new IllegalArgumentException("Account have balance left");
        }

        List<SecurityOrder> securityOrders = securityOrderDao.findByAccountId(account.getId());
        for (SecurityOrder securityOrder : securityOrders){
            securityOrderDao.deleteById(securityOrder.getId());
        }
        accountDao.deleteById(traderId);
        traderDao.deleteById(traderId);
    }

    /**
     * Deposit a fund to an account by traderId
     * @param traderId must not be null
     * @param fund must be greater than 0
     * @return updated account
     */
    public Account deposit(Integer traderId, Double fund) {
        if (traderId == null) {
            throw new IllegalArgumentException("Trader Id cannot be null");
        }

        if (fund <= 0 ){
            throw new IllegalArgumentException("Fund must be greater than 0");
        }

        if (traderId == null) {
            throw new IllegalArgumentException("Trader ID cannot be null");
        }

        Account account = accountDao.findByTraderId(traderId).get(0);
        if (account == null) {
            throw new IllegalArgumentException("Trader account cannot be found");
        }

        account.setAmount(account.getAmount() + fund);
        return accountDao.save(account);
    }

    /**
     * Withdraw a fund to an account by traderId
     * @param traderId must not be null
     * @param fund must be greater than 0
     * @return updated account
     */
    public Account withdraw(Integer traderId, Double fund) {
        if (traderId == null) {
            throw new IllegalArgumentException("Trader Id cannot be null");
        }

        if (fund <= 0 ){
            throw new IllegalArgumentException("Fund must be greater than 0");
        }

        if (traderId == null) {
            throw new IllegalArgumentException("Trader ID cannot be null");
        }

        Account account = accountDao.findByTraderId(traderId).get(0);
        if (account == null) {
            throw new IllegalArgumentException("Trader account cannot be found");
        }

        Double amount = account.getAmount() - fund;
        if (amount < 0) {
            throw new IllegalArgumentException("Insufficient fund");
        }
        account.setAmount(amount);
        return accountDao.save(account);
    }
}
