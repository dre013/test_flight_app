package by.dre.je.jdbc;

import by.dre.je.jdbc.dao.FlightDao;
import by.dre.je.jdbc.dao.TicketDao;
import by.dre.je.jdbc.dto.TicketFilter;
import by.dre.je.jdbc.entity.Flight;
import by.dre.je.jdbc.entity.Ticket;

import java.math.BigDecimal;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        var ticketDao = TicketDao.getInstance();
//        Ticket ticket = new Ticket();
//        ticket.setPassportNo("asd12343");
//        ticket.setPassengerName("Pavel");
//        ticket.setFlightId(4L);
//        ticket.setSeatNo("53B");
//        ticket.setCost(BigDecimal.TEN);
//
//        System.out.println(ticketDao.save(ticket));
//
//        System.out.println(ticketDao.delete(2L));

        TicketFilter filter = new TicketFilter(null, null, 5, 0);
        System.out.println(ticketDao.findAll(filter));
//        Ticket ticket = ticketDao.findById(3L);
//        System.out.println(ticket);
//        ticket.setSeatNo("38C");
//        System.out.println(ticketDao.update(ticket));
//        System.out.println(ticket);
        var flightDao = FlightDao.getInstance();
        System.out.println(flightDao.findAll());
        System.out.println(flightDao.findById(4L));
        System.out.println(ticketDao.findAllByFlightId(4L));
    }
}
