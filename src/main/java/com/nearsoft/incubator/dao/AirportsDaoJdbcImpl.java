package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.bo.Airport;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by edgar on 16/06/14.
 */
@Component("jdbcAirportsDao")
public class AirportsDaoJdbcImpl extends JdbcDaoSupport implements AirportsDao {

    @Override
    public List<Airport> getAllAirports() {
        String sql = "select * from airport";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Airport.class));
    }

    @Override
    public void save(final List<Airport> airports) {
        String sql = "insert into airport (iata, city, name, countryName, creationDate) values (?, ?, ?, ?, ?)";
        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Airport airport = airports.get(i);
                preparedStatement.setString(1, airport.getIata());
                preparedStatement.setString(2, airport.getCity());
                preparedStatement.setString(3, airport.getName());
                preparedStatement.setString(4, airport.getCountryName());
                preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));
            }

            @Override
            public int getBatchSize() {
                return airports.size();
            }
        });
    }

    @Override
    public void deleteAll() {
        String sql = "delete from airport";
        getJdbcTemplate().update(sql);
    }
}
