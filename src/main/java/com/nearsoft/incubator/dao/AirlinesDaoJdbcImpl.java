package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.bo.Airline;
import com.nearsoft.incubator.util.Airlines;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar on 20/06/14.
 */
@Component("jdbcAirlinesDao")
public class AirlinesDaoJdbcImpl extends JdbcDaoSupport implements AirlinesDao {

    @Override
    public Map<String, Airline> getAirlinesMap() {
        String sql = "select * from airline";
        List<Airline> airlines =  getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Airline.class));
        return Airlines.toAirlinesMap(airlines);
    }

    @Override
    public void save(final Map<String, Airline> airlines) {
        String sql = "insert into airline (fs, name, creationDate) values (?, ?, ?)";
        final List<Airline> airlineList = new ArrayList<Airline>(airlines.values());
        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Airline airline = airlineList.get(i);
                preparedStatement.setString(1, airline.getFs());
                preparedStatement.setString(2, airline.getName());
                preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
            }

            @Override
            public int getBatchSize() {
                return airlines.size();
            }
        });
    }

    @Override
    public void deleteAll() {
        String sql = "delete from airline";
        getJdbcTemplate().update(sql);
    }
}
