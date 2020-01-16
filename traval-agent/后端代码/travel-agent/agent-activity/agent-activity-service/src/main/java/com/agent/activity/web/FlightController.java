package com.agent.activity.web;

import com.agent.activity.pojo.Flight;
import com.agent.activity.service.FlightService;
import com.netflix.discovery.converters.Auto;
import org.bouncycastle.util.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("activity/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Flight>> getAllFlight(){
        return ResponseEntity.ok(flightService.getAllFlight());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteFlight(
            @RequestParam(value = "flight_id") String flight_id
    ){
        flightService.deleteFlight(flight_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateTraveller(
            @RequestParam(value = "flight_id") String flight_id,
            @RequestParam(value = "flight_company") String flight_company,
            @RequestParam(value = "start_time") String raw_start_time,
            @RequestParam(value = "end_time") String raw_end_time,
            @RequestParam(value = "start_address") String start_address,
            @RequestParam(value = "end_address") String end_address
    ){
        Timestamp start_time = new Timestamp(Long.parseLong(raw_start_time));
        Timestamp end_time = new Timestamp(Long.parseLong(raw_end_time));
        flightService.updateFlight(flight_id, flight_company, start_time, end_time,
                start_address, end_address);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertCompany(
            @RequestParam(value = "flight_id") String flight_id,
            @RequestParam(value = "flight_company") String flight_company,
            @RequestParam(value = "start_time") String raw_start_time,
            @RequestParam(value = "end_time") String raw_end_time,
            @RequestParam(value = "start_address") String start_address,
            @RequestParam(value = "end_address") String end_address
    ){
        Timestamp start_time = new Timestamp(Long.parseLong(raw_start_time));
        Timestamp end_time = new Timestamp(Long.parseLong(raw_end_time));
        flightService.insertFlight(flight_id, flight_company, start_time, end_time,
                start_address, end_address);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
