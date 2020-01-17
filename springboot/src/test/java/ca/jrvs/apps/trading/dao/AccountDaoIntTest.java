package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class AccountDaoIntTest {

    @Autowired
    private AccountDao  accountDao;
    @Autowired
    private TraderDao traderDao;

    private Trader savedTrader;
    private Account savedAccount;


    @Before
    public void insertOne() {
        savedTrader = new Trader();
        savedTrader.setCountry("Canada");
        savedTrader.setDob(new Date(1995,02,03));
        savedTrader.setEmail("james@jrvs.ca");
        savedTrader.setFirstName("James");
        savedTrader.setLastName("Li");
        savedTrader.setId(traderDao.save(savedTrader).getId());

        savedAccount = new Account();
        savedAccount.setTraderId(savedTrader.getId());
        savedAccount.setAmount(100d);
        savedAccount.setId(accountDao.save(savedAccount).getId());

    }

    @After
    public  void deleteOne() {
        accountDao.deleteById(savedAccount.getId());
        traderDao.deleteById(savedTrader.getId());
        assertEquals(0, accountDao.count());
    }

    @Test
    public void findAllById() {
        List<Account> accounts = Lists
                .newArrayList(accountDao.findAllById(Arrays.asList(savedAccount.getId())));
        assertEquals(1, accounts.size());
        assertEquals(savedAccount.getAmount(), accounts.get(0).getAmount());
        assertEquals(savedAccount.getTraderId(), accounts.get(0).getTraderId());

    }
}