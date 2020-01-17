package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import com.sun.org.apache.xpath.internal.operations.Quo;
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
public class SecurityOrderDaoIntTest {

    @Autowired
    private SecurityOrderDao securityOrderDao;
    @Autowired
    private AccountDao  accountDao;
    @Autowired
    private TraderDao traderDao;
    @Autowired
    private QuoteDao quoteDao;

    private SecurityOrder savedSecurityOrder;
    private Trader savedTrader;
    private Account savedAccount;
    private Quote savedQuote;

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

        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setTicker("aapl");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);

        savedSecurityOrder = new SecurityOrder();
        savedSecurityOrder.setAccountId(savedAccount.getId());
        savedSecurityOrder.setNotes("Good");
        savedSecurityOrder.setPrice(10d);
        savedSecurityOrder.setSize(10);
        savedSecurityOrder.setTicker(savedQuote.getTicker());
        savedSecurityOrder.setStatus("Complete");
        savedSecurityOrder.setId(securityOrderDao.save(savedSecurityOrder).getId());
    }

    @After
    public  void deleteOne() {
        securityOrderDao.deleteById(savedSecurityOrder.getId());
        quoteDao.deleteById(savedQuote.getTicker());
        accountDao.deleteById(savedAccount.getId());
        traderDao.deleteById(savedTrader.getId());
        assertEquals(0, securityOrderDao.count());
    }

    @Test
    public void findAllById() {
        List<SecurityOrder> securityOrders = Lists
                .newArrayList(securityOrderDao.findAllById(Arrays.asList(savedSecurityOrder.getId())));
        assertEquals(1, securityOrders.size());
        assertEquals(savedSecurityOrder.getAccountId(), securityOrders.get(0).getAccountId());
        assertEquals(savedSecurityOrder.getNotes(), securityOrders.get(0).getNotes());
        assertEquals(savedSecurityOrder.getPrice(), securityOrders.get(0).getPrice());
        assertEquals(savedSecurityOrder.getSize(), securityOrders.get(0).getSize());
        assertEquals(savedSecurityOrder.getStatus(), securityOrders.get(0).getStatus());
        assertEquals(savedSecurityOrder.getTicker(), securityOrders.get(0).getTicker());

    }

}