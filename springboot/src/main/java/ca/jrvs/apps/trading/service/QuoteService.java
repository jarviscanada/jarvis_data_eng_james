package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Update quote table against IEX source
     * -get full quotes from db
     * -foreach ticker get iexQuote
     * -convert iexQuote to quote entity
     * -persist quote to db
     *
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public void updateMarketData() {
        List<Quote> dbQuotes = quoteDao.findAll();
        for (Quote quote : dbQuotes) {
            IexQuote iexQuote = marketDataDao.findById(quote.getTicker()).get();
            quoteDao.save(buildQuoteFromIexQuote(iexQuote));
        }
    }

    /**
     *Helper method. Map a IexQuote to a Quote entity
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote updateQuote = new Quote();
        updateQuote.setTicker((String) iexQuote.getSymbol());
        updateQuote.setLastPrice(iexQuote.getLatestPrice());
        updateQuote.setAskPrice(iexQuote.getIexAskPrice());
        updateQuote.setAskSize(iexQuote.getIexAskSize());
        updateQuote.setBidPrice(iexQuote.getIexBidPrice());
        updateQuote.setBidSize(iexQuote.getIexBidSize());
        return updateQuote;
    }

    public IexQuote findIexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker)
                .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
    }

    /**
     * Validate (against IEX) and save given tickers to quote table
     * @param tickers a list of tickers/symbols
     * @throws IllegalArgumentException if ticker is not found from IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        List<Quote> quotes = new ArrayList<>();

        for (String ticker : tickers) {

            IexQuote iexQuote;
            try {
                iexQuote = findIexQuoteByTicker(ticker);
            } catch (DataRetrievalFailureException e) {
                throw new IllegalArgumentException("Ticker not found from IEX");
            }

            quotes.add(saveQuote(buildQuoteFromIexQuote(iexQuote)));
        }

        return quotes;
    }

    /**
     * helper method
     */
    public Quote saveQuote(String ticker) {
        return saveQuotes(Collections.singletonList(ticker)).get(0);
    }

    /**
     * update a given quote to quote table without validation
     * @param quote entity
     */
    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    /**
     * Find all quotes from the quote table
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes() {
        return quoteDao.findAll();
    }
}
