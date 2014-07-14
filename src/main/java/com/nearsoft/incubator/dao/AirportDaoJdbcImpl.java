package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.model.Airport;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * Created by edgar on 16/06/14.
 */
@Component("airportDaoJdbcImpl")
public class AirportDaoJdbcImpl extends JdbcDao<Airport> {

    private static final String TABLE_NAME = "airport";

    public AirportDaoJdbcImpl(){
        super(Airport.class);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected List<String> getColumnNames() {
        return Arrays.asList("iata", "name", "city", "countryName", "creationDate");
    }

    @Override
    protected void setValues(PreparedStatement preparedStatement, Airport airport) throws SQLException{
        preparedStatement.setString(1, airport.getIata());
        preparedStatement.setString(2, airport.getName());
        preparedStatement.setString(3, airport.getCity());
        preparedStatement.setString(4, airport.getCountryName());
        preparedStatement.setTimestamp(5, new Timestamp(airport.getCreationDate().getTime()));
    }
}
