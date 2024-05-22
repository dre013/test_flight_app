package by.dre.je.jdbc.dao;

import by.dre.je.jdbc.entity.Flight;
import by.dre.je.jdbc.entity.FlightStatus;
import by.dre.je.jdbc.entity.Ticket;
import by.dre.je.jdbc.exception.DaoException;
import by.dre.je.jdbc.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightDao implements Dao<Long, Flight> {
    private static final FlightDao INSTANCE = new FlightDao();

    private static final String SELECT_ALL_SQL = """
                    SELECT id, flight_no, departure_date, departure_airport_code, arrival_date, aircraft_id, status
                    FROM flight
                    """;

    private static final String FIND_BY_ID_SQL = """
                    SELECT id, flight_no, departure_date, departure_airport_code, arrival_date, aircraft_id, status
                    FROM flight
                    WHERE id = ?
                    """;

    @Override
    public boolean update(Flight flight) {
        return false;
    }

    @Override
    public Flight findById(Long id) {
        try (var connection = ConnectionManager.get()){
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Flight findById(Long id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            Flight flight = null;
            if (result.next()) {
                flight = buildFlight(result);
            }
            return flight;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Flight> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SELECT_ALL_SQL)) {
            var result = statement.executeQuery();
            List<Flight> flights = new ArrayList<>();
            while (result.next()) {
                flights.add(
                        buildFlight(result)
                );
            }
            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Flight buildFlight(ResultSet result) throws SQLException {
        return new Flight(
                result.getLong("id"),
                result.getString("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getString("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status")));
    }

    @Override
    public Flight save(Flight flight) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    private FlightDao() {
    }
}
