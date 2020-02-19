package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.plaf.synth.SynthTextAreaUI;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;

    @Before
    public void insertOne() {
        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setTicker("aapl");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);
        savedQuote.setAskPrice(9d);
        savedQuote.setAskSize(9);
        savedQuote.setBidPrice(9.2d);
        savedQuote.setBidSize(9);
        savedQuote.setTicker("jrvs");
        savedQuote.setLastPrice(9.1d);
        quoteDao.save(savedQuote);
        savedQuote.setAskPrice(8d);
        savedQuote.setAskSize(8);
        savedQuote.setBidPrice(8.2d);
        savedQuote.setBidSize(8);
        savedQuote.setTicker("jams");
        savedQuote.setLastPrice(8.1d);
        quoteDao.save(savedQuote);
    }

    @Test
    public void count() {
        assertEquals(3, quoteDao.count());
    }

    @Test
    public void findById() {
        Quote quote = quoteDao.findById(savedQuote.getTicker()).get();
        assertEquals(quote.getAskPrice(), savedQuote.getAskPrice());
        assertEquals(quote.getAskSize(), savedQuote.getAskSize());
        assertEquals(quote.getBidPrice(), savedQuote.getBidPrice());
        assertEquals(quote.getBidSize(), savedQuote.getBidSize());
        assertEquals(quote.getTicker(), savedQuote.getTicker());
    }

    @Test
    public void findAll() {
        Quote quote =quoteDao.findAll().get(2);
        assertEquals(quote.getAskPrice(), savedQuote.getAskPrice());
        assertEquals(quote.getAskSize(), savedQuote.getAskSize());
        assertEquals(quote.getBidPrice(), savedQuote.getBidPrice());
        assertEquals(quote.getBidSize(), savedQuote.getBidSize());
        assertEquals(quote.getTicker(), savedQuote.getTicker());
    }

    @After
    public void deleteOne() {
        quoteDao.deleteById(savedQuote.getTicker());
        assertEquals(2, quoteDao.count());
        quoteDao.deleteAll();
        assertEquals(0, quoteDao.count());
    }
}