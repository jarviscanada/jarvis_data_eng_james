package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;

    @Before
    public void setup() {
        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setTicker("AAPL");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);
        savedQuote.setAskPrice(9d);
        savedQuote.setAskSize(9);
        savedQuote.setBidPrice(9.2d);
        savedQuote.setBidSize(9);
        savedQuote.setTicker("MSFT");
        savedQuote.setLastPrice(9.1d);
        quoteDao.save(savedQuote);
    }

    @Test
    public void findIexQuoteByTicker() {
        assertNotNull(quoteService.findIexQuoteByTicker("AAPL"));
    }

    @Test
    public void updateMarketData() {
        quoteService.updateMarketData();
        assertEquals(2, quoteDao.count());
    }

    @Test
    public void saveQuotes() {
        List<String> list = Arrays.asList("AAPL","MSFT","GOOG");
        quoteService.saveQuotes(list);
        assertEquals(3, quoteDao.count());
    }

    @Test
    public void saveQuote() {
        Quote quote = new Quote();
        quote.setAskPrice(8d);
        quote.setAskSize(8);
        quote.setBidSize(8);
        quote.setBidPrice(8.2d);
        quote.setLastPrice(8.1d);
        quote.setTicker("NKE");
        quoteService.saveQuote(quote);
        assertNotNull(quoteService.findIexQuoteByTicker("NKE"));
        quoteService.saveQuote("JD");
        assertNotNull(quoteService.findIexQuoteByTicker("JD"));
    }

    @Test
    public void findAllQuotes() {
        List<Quote> list = quoteService.findAllQuotes();
        assertNotNull(list);
    }

    @After
    public void clean() {
        quoteDao.deleteAll();
    }

}