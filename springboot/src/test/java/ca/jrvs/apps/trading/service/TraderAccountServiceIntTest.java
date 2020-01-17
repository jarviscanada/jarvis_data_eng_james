package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

    private TraderAccountView savedView;
    @Autowired
    private TraderAccountService traderAccountService;
    @Autowired
    private TraderDao traderDao;
    @Autowired
    private AccountDao accountDao;

    private Trader savedTrader;

    @Before
    public void setup() {
        savedTrader = new Trader();
        savedTrader.setCountry("Canada");
        savedTrader.setDob(new Date(1995,02,03));
        savedTrader.setEmail("james@jrvs.ca");
        savedTrader.setFirstName("James");
        savedTrader.setLastName("Li");

        savedView = traderAccountService.createTraderAndAccount(savedTrader);
        savedTrader = savedView.getTrader();
    }

    @Test
    public void changeBalance() {
        traderAccountService.deposit(savedTrader.getId(),100d);
        traderAccountService.withdraw(savedTrader.getId(),50d);

        try {
            traderAccountService.deposit(savedTrader.getId(), -100d);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        //insufficent fund
        try {
            traderAccountService.withdraw(savedTrader.getId(), 100d);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    @After
    public void delete() {
        try {
            traderAccountService.deleteTraderById(savedView.getTrader().getId());
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        traderAccountService.withdraw(savedTrader.getId(), 50d).getAmount();
        traderAccountService.deleteTraderById(savedTrader.getId());
    }

}