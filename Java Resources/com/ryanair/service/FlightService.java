package com.ryanair.service;

import java.util.List;

import com.ryanair.core.FlightData;
import com.ryanair.core.LegData;
import com.ryanair.core.Route;
import com.ryanair.core.Schedule;

public interface FlightService {

	List<FlightData> searchFlights(LegData flightConditions);
	List<Schedule> getSchedules(String departure, String arrival, int year, int month);
	List<Route> getAllRoutes();
}
