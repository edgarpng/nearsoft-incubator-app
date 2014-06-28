package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.bo.PersistableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar on 26/06/14.
 *
 */
public abstract class JdbcDao<T extends PersistableEntity> extends JdbcDaoSupport implements Dao<T> {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcDao.class);
    protected Class<T> clazz;

    public JdbcDao(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public List<T> findAll() {
        final String sql = buildSelectQuery();
        LOG.info("Jdbc Dao: {}", sql);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(clazz));
    }

    @Override
    public void deleteAll() {
        final String sql = buildDeleteQuery();
        LOG.info("Jdbc Dao: {}", sql);
        getJdbcTemplate().update(sql);
    }

    @Override
    public void saveAll(List<T> objects){
        final JdbcDao<T> dao = this;
        final String sql = buildInsertQuery();
        //Convert to ArrayList to get constant random access and thus linear time execution for batchUpdate()
        final ArrayList<T> arrayList = new ArrayList<T>(objects);
        LOG.info("Jdbc Dao: {}", sql);

        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                T object = arrayList.get(i);
                dao.setValues(preparedStatement, object);
            }

            @Override
            public int getBatchSize() {
                return arrayList.size();
            }
        });
    }

    private String buildSelectQuery(){
        return String.format("select * from %s", getTableName());
    }

    private String buildDeleteQuery(){
        return String.format("delete from %s", getTableName());
    }

    private String buildInsertQuery(){
        StringBuilder columns = new StringBuilder();
        StringBuilder questionMarks = new StringBuilder();
        String comma = "";
        String questionMark = "?";
        for(String column: getColumnNames()){
            columns.append(comma);
            columns.append(column);
            questionMarks.append(comma);
            questionMarks.append(questionMark);
            comma = ",";
        }
        return String.format("insert into %s (%s) values (%s)", getTableName(), columns.toString(), questionMarks.toString());
    }

    protected abstract String getTableName();

    protected abstract List<String> getColumnNames();

    protected abstract void setValues(PreparedStatement preparedStatement, T object) throws SQLException;
}
