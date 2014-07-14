package com.nearsoft.incubator.dao;

import com.nearsoft.incubator.model.Airline;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * Created by edgar on 20/06/14.
 */
@Component("airlineDaoJdbcImpl")
public class AirlineDaoJdbcImpl extends JdbcDao<Airline> {

    private static final String TABLE_NAME = "airline";

    public AirlineDaoJdbcImpl(){
        super(Airline.class);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected List<String> getColumnNames() {
        return Arrays.asList("flightStatsId", "name", "creationDate");
    }

    @Override
    protected void setValues(PreparedStatement preparedStatement, Airline airline) throws SQLException{
        preparedStatement.setString(1, airline.getFlightStatsId());
        preparedStatement.setString(2, airline.getName());
        preparedStatement.setTimestamp(3, new Timestamp(airline.getCreationDate().getTime()));
    }
}
