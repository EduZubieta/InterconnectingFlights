package com.ryanair.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ryanair.core.FlightData;
import com.ryanair.core.LegData;
import com.ryanair.service.FlightService;
 
@RestController
public class FlightController {
 
    @Autowired
    FlightService flightService;  //Service which will do all data retrieval/manipulation work
 
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String welcome() {
        return "Welcome to RestTemplate Example.";
    }
     
    //-------------------Retrieve Flights --------------------------------------------------------
     
    @RequestMapping(value = "/interconnections", method = RequestMethod.GET)
    public ResponseEntity<List<FlightData>> getFlights(
    		final @RequestParam(value = "departure") String departure,
            final @RequestParam(value = "arrival") String arrival,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,
            final @RequestParam(value = "arrivalDateTime")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDateTime)
    		) {
        
    	LegData flightConditions = new legData(departure, arrival, departureDateTime, arrivalDateTime);
    	List<FlightData> flights = flightService.searchFlights(flightConditions);
        if(flights.isEmpty()){
            return new ResponseEntity<List<FlightData>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<FlightData>>(flights, HttpStatus.OK);
    }
}
