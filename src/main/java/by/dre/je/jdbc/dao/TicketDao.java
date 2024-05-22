package by.dre.je.jdbc.dao;

import by.dre.je.jdbc.dto.TicketFilter;
import by.dre.je.jdbc.entity.Flight;
import by.dre.je.jdbc.entity.FlightStatus;
import by.dre.je.jdbc.entity.Ticket;
import by.dre.je.jdbc.exception.DaoException;
import by.dre.je.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketDao implements Dao<Long, Ticket> {
    private static final TicketDao INSTANCE = new TicketDao();
    private static final FlightDao flightDao = FlightDao.getInstance();

    private static final String SAVE_SQL = """
            INSERT INTO ticket
            (passport_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM ticket
            WHERE id = ?
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT t.id, t.passport_no, t.passenger_name, t.flight_id, t.seat_no, t.cost,
            f.flight_no, f.departure_date, f.departure_airport_code, f.arrival_date, f.aircraft_id, f.status
            FROM ticket t
            JOIN public.flight f on f.id = t.flight_id
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, passport_no, passenger_name, flight_id, seat_no, cost FROM ticket
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE ticket
            SET passport_no = ?,
                passenger_name = ?,
                flight_id = ?,
                seat_no = ?,
                cost = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_BY_FLIGHT_ID = SELECT_ALL_SQL + """
            WHERE t.flight_id = ?""";

    @Override
    public boolean update(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.setLong(6, ticket.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Ticket findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            Ticket ticket = null;
            if (result.next()) {
                ticket = buildTicket(result);
            }
            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSQL = new ArrayList<>();
        if (filter.passengerName() != null) {
            parameters.add(filter.passengerName());
            whereSQL.add("passenger_name = ?");
        }
        if (filter.seatNo() != null) {
            parameters.add("%" + filter.seatNo() + "%");
            whereSQL.add("seat_no like ?");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        var where = whereSQL.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : "",
                " LIMIT ? OFFSET ?"
        ));

        String sql = SELECT_ALL_SQL + where;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            var result = statement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (result.next()) {
                tickets.add(
                        buildTicket(result)
                );
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Ticket> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SELECT_ALL_SQL)) {
            var result = statement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (result.next()) {
                tickets.add(
                        buildTicket(result)
                );
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAllByFlightId(Long id) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_BY_FLIGHT_ID)) {
            statement.setLong(1, id);
            List<Ticket> tickets = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(buildTicket(result));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Ticket buildTicket(ResultSet result) throws SQLException {
        return new Ticket(result.getLong("id"),
                result.getString("passport_no"),
                result.getString("passenger_name"),
                flightDao.findById(
                        result.getLong("flight_id"),
                        result.getStatement().getConnection()),
                result.getString("seat_no"),
                result.getBigDecimal("cost"));
    }

    @Override
    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();

            if (keys.next())
                ticket.setId(keys.getLong("id"));

            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    private TicketDao() {
    }
}
