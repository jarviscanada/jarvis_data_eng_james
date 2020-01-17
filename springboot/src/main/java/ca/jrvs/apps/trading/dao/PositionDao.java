package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PositionDao extends JdbcCrudDao<Position>{

    private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

    private final String TABLE_NAME = "position";
    private final String ID_COLUMN = "account_id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired PositionDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COLUMN;
    }

    @Override
    Class<Position> getEntityClass() {
        return Position.class;
    }

    @Override
    public int updateOne(Position entity) {
        throw new UnsupportedOperationException("This is a read-Only view.");
    }

    @Override
    public void delete(Position securityOrder) {
        throw new UnsupportedOperationException("This is a read-Only view.");
    }

    @Override
    public void deleteAll(Iterable<? extends Position> iterable) {
        throw new UnsupportedOperationException("This is a read-Only view.");
    }

    @Override
    public <S extends Position> S save(S entity) {
        throw new UnsupportedOperationException("This is a read-Only view.");
    }

    @Override
    public <S extends Position> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("This is a read-Only view.");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("This is a read-Only view.");
    }


    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("This is a read-Only view.");
    }

    public List<Position> findByIdList(Integer Id) {
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
        return getJdbcTemplate().query(selectSql, BeanPropertyRowMapper.newInstance(Position.class), Id);
    }
}
