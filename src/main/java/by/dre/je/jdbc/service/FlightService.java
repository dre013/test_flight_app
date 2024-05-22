package by.dre.je.jdbc.service;

import by.dre.je.jdbc.dao.FlightDao;
import by.dre.je.jdbc.dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getInstance();

    public List<FlightDto> findAll() {
        return flightDao.findAll().stream().map(flight -> new FlightDto(flight.getId(),
                "%s - %s - %s".formatted(flight.getArrivalDate(),
                        flight.getDepartureAirportCode(),
                        flight.getStatus()))).collect(Collectors.toList());
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }

    private FlightService() {
    }
}
