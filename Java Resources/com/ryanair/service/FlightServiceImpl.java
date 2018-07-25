package com.ryanair.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ryanair.core.Flight;
import com.ryanair.core.FlightData;
import com.ryanair.core.LegData;
import com.ryanair.core.Route;
import com.ryanair.core.Schedule;

@Service("flightService")
public class FlightServiceImpl implements FlightService{

	
	public List<FlightData> searchFlights(LegData flightConditions) {

		String origin = flightConditions.getDepartureAirport();
		String destination = flightConditions.getArrivalAirport();
		
		// we declare 3 lists to categorize the flights
		List<Route> directRoute = new ArrayList<Route>();
		List<Route> originRoute = new ArrayList<Route>();
		List<Route> destinationRoute = new ArrayList<Route>();
		
		// first, we get all the routes
		ArrayList routesArrayListFromGSON = this.getAllRoutes();
		
		//we categorize the routes
		for (int i = 0; i < routesArrayListFromGSON.size(); i++) {
			// this is the string of the json object
			String json = routesArrayListFromGSON.get(i).toString();
			
			// PATCH: this fixes the problem with the malformed json object ("connectingAirport": "",)
			json = json.replaceAll("=," , "=null,");
			// converting the string to our POJO
			Route route = new Gson().fromJson(json, Route.class);
			
			if(origin.equals(route.getAirportFrom()) && destination.equals(route.getAirportTo())) {
				// direct route
				directRoute.add(route);
			}else if(origin.equals(route.getAirportFrom())) {
				// origin route for later treatment
				originRoute.add(route);
			}else if(destination.equals(route.getAirportTo())) {
				// destination route for later treatment
				destinationRoute.add(route);
			}
		}
		
		// we handle both origin and destination lists
		List<Route> listaIndirectos = new ArrayList<Route>();
		for (int j = 0; j < originRoute.size(); j++) {
			Route origen = originRoute.get(j);
			
			for(int d = 0; d < destinationRoute.size(); d++) {
				Route destino = destinationRoute.get(d);
				
				// so we can get interconnection flights
				if(origen.getAirportTo().equals(destino.getAirportFrom())) {
					listaIndirectos.add(origen);
					listaIndirectos.add(destino);
					destinationRoute.remove(d);
					d = destinationRoute.size();
				}
			}
		}
		
		
		// now we have to deal with both lists, direct flights and indirect ones
		
		// direct flights
		List<Flight> vuelosD = this.getFlightsFromLeg(flightConditions);

		
		// indirect flights
		for(int n = 0; n < listaIndirectos.size(); n=n+2) {
			List<Flight> vuelosA = new ArrayList<Flight>();
			List<Flight> vuelosB = new ArrayList<Flight>();
			Route rutaA = listaIndirectos.get(n);
			Route rutaB = listaIndirectos.get(n+1);
			
			// we require 2 LegData for calling getFlights
			LegData legA = new LegData
					(rutaA.getAirportFrom(), rutaA.getAirportTo(),flightConditions.getDepartureDateTime(),flightConditions.getArrivalDateTime());
			LegData legB = new LegData
					(rutaB.getAirportFrom(), rutaB.getAirportTo(),flightConditions.getDepartureDateTime(),flightConditions.getArrivalDateTime());
			try {
				vuelosA = this.getFlightsFromLeg(legA);
				vuelosB = this.getFlightsFromLeg(legB);
				
				// if there are coincidences
				if(vuelosA.size() > 0 && vuelosB.size() > 0) {					
					//TODO
					//check arrival time and departure time difference is >=2h
					// add valid flights to final list
				}

			}catch(HttpClientErrorException hcee) {
				// There is not an available route
			}
		}
		
		//TODO
		// we handle all lists and create a list of FlightData
		List<FlightData> returnList = new ArrayList<FlightData>();
		// handling goes here
		
		return returnList;
	}
	
	public ArrayList getAllRoutes(){
		RestTemplate restTemplate = new RestTemplate();
		String routes = restTemplate.getForObject("https://api.ryanair.com/core/3/routes", String.class);
		
		String jsonArrayString = routes;
		JsonParser jsonParser = new JsonParser();
		JsonArray arrayFromString = jsonParser.parse(jsonArrayString).getAsJsonArray();
		
		Gson googleJson = new Gson();
		ArrayList javaArrayListFromGSON = googleJson.fromJson(arrayFromString, ArrayList.class);
		return javaArrayListFromGSON;
	}
	
	public List<Flight> getFlightsFromLeg(LegData flightConditions){
		
		LocalDateTime arrivalDateTime;
		// base url
		String url = "https://api.ryanair.com/timetable/3/schedules/";
		// add proper params
		url += flightConditions.getDepartureAirport() + "/";
		url += flightConditions.getArrivalAirport() + "/";
		url += "years/" + flightConditions.getDepartureDateTime().getYear();
		url += "/months/" + flightConditions.getDepartureDateTime().getMonthValue();
		
		RestTemplate restTemplate = new RestTemplate();
		//System.out.println(url);
		String schedules = restTemplate.getForObject(url, String.class);
		JsonParser jsonParser = new JsonParser();
		Gson googleJson = new Gson();
				
		JsonObject a = googleJson.fromJson(schedules, JsonObject.class);
		JsonArray jaDias =  jsonParser.parse(a.get("days").toString()).getAsJsonArray();
		List<Flight> vuelos = new ArrayList<Flight>();
				
		for(int i = 0; i < jaDias.size(); i++) {
			JsonElement json = jaDias.get(i);

			JsonObject jsonDay = googleJson.fromJson(json, JsonObject.class);
			if(Integer.parseInt(jsonDay.get("day").toString().trim()) >= flightConditions.getDepartureDateTime().getDayOfMonth()
			&& Integer.parseInt(jsonDay.get("day").toString().trim()) <= flightConditions.getArrivalDateTime().getDayOfMonth()){
							
				JsonArray jaVuelos = jsonParser.parse(jsonDay.get("flights").toString()).getAsJsonArray();
				for(int j = 0; j < jaVuelos.size(); j++) {
					Flight flight = new Gson().fromJson(jaVuelos.get(j), Flight.class);
					flight.setDay(Integer.parseInt(jsonDay.get("day").toString().trim()));
					
					// origin time must be equals or lower than current flight time
					if(
					((flightConditions.getDepartureHour() == flight.getArrivalHour() 
					&& flightConditions.getDepartureMinute() <= flight.getArrivalMinute()
					|| flightConditions.getDepartureHour() <= flight.getArrivalHour()))
							
					// destination time must be equals or greater than current flight time
					|| (flightConditions.getArrivalHour() == flight.getDepartureHour() 
					&& flightConditions.getArrivalMinute() >= flight.getArrivalMinute())
					|| flightConditions.getArrivalHour() >= flight.getDepartureHour())
						vuelos.add(flight);
				}
			}
		}
		return vuelos;
	}
	
	
	

}
