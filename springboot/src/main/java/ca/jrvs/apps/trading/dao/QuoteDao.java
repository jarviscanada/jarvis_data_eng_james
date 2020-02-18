package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public Quote save(Quote quote) {
        if (existsById(quote.getId())) {
            int updatedRowCount = updateOne(quote);
            if (updatedRowCount != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(quote);
        }
        return quote;
    }

    /**
     * helper method that saves one quote
     */
    private void addOne(Quote quote) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(sqlParameterSource);
        if (row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    /**
     * helper method that updates one quote
     */
    private int updateOne(Quote quote) {
        String update_sql = "update quote set last_price=?, bid_price=?, bid_size=?, ask_price=?,"
                + "ask_size=? where ticker=?";
        return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
    }

    /**
     * helper method that makes sql update values objects
     */
    private Object[] makeUpdateValues(Quote quote) {
        return new Object[]{quote.getLastPrice(), quote.getBidPrice(), quote.getBidSize(),
                quote.getAskPrice(), quote.getAskSize(), quote.getId()};
    }

    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {
        List<S> list = new ArrayList<>();
        for (Quote quote : quotes) {
            list.add((S) save(quote));
        }
        return list;
    }

    /**
     * Find a quote by ticker
     * @param ticker name
     * @return quote or Optional.empty if notf ound
     */
    @Override
    public Optional<Quote> findById(String ticker) {
        Quote t = null;
        String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";

        try {
            t = (Quote) jdbcTemplate
                    .queryForObject(selectSql,
                            BeanPropertyRowMapper.newInstance(Quote.class), ticker);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find ticker:" + ticker, e);
        }
        if (t == null) {
            throw new ResourceNotFoundException("Resource not found");
        }
        return Optional.of(t);
    }

    @Override
    public boolean existsById(String ticker) {
        String countSql = "SELECT count(*) FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        boolean sw = false;
        if (jdbcTemplate.queryForObject(countSql, Integer.class, ticker) == 1) {
            sw = true;
        }
        return sw;
    }


    /**
     *return all quotes
     */
    @Override
    public List<Quote> findAll() {
        String selectSql = "SELECT * FROM " + TABLE_NAME;
        return  jdbcTemplate
                .query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        String countSql = "SELECT count(*) FROM " + TABLE_NAME ;
        return jdbcTemplate.queryForObject(countSql, Long.class);
    }

    @Override
    public void deleteById(String ticker) {
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        jdbcTemplate.update(deleteSql, ticker);
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM " + TABLE_NAME;
        jdbcTemplate.update(deleteSql);
    }
}
