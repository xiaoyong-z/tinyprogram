package com.agent.activity.service;

import com.agent.activity.mapper.FlightMapper;
import com.agent.activity.pojo.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightMapper flightMapper;

    public List<Flight> getAllFlight() {
        return flightMapper.getAllFlight();
    }

    public void deleteFlight(String flight_id) {
        flightMapper.deleteFlight(flight_id);
    }

    public void updateFlight(String flight_id, String flight_company,
                               Timestamp start_time, Timestamp end_time,
                               String start_address, String end_address) {
        Flight flight = new Flight();
        flight.setFlight_id(flight_id);
        flight.setFlight_company(flight_company);
        flight.setStart_time(start_time);
        flight.setEnd_time(end_time);
        flight.setStart_address(start_address);
        flight.setEnd_address(end_address);
        flightMapper.updateFlight(flight);
    }

    public void insertFlight(String flight_id, String flight_company,
                             Timestamp start_time, Timestamp end_time,
                             String start_address, String end_address) {
        Flight flight = new Flight();
        flight.setFlight_id(flight_id);
        flight.setFlight_company(flight_company);
        flight.setStart_time(start_time);
        flight.setEnd_time(end_time);
        flight.setStart_address(start_address);
        flight.setEnd_address(end_address);
        flightMapper.insertFlight(flight);
    }
}
