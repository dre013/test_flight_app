package by.dre.je.jdbc.dto;

public record TicketFilter(String passengerName,
                           String seatNo,
                           int limit,
                           int offset) {
}
