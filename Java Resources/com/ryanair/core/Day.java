package com.ryanair.core;

import java.util.List;

public class Day {
	private int day;
	private List<Flight> flights;
	
	public Day() {}
	
	public Day(int day, List<Flight> flights) {
		this.day = day;
		this.flights = flights;
	}

	public int getDay() {
		return day;
	}
	
	public void setDay(int dayNumber) {
		this.day = dayNumber;
	}
	
	public List<Flight> getFlights() {
		return flights;
	}
	
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	
//	@Override
//    public String toString() {
//		String value = "";
//        value += "Day [day=" + day + "," + 
//        		"flights=[" ;
//        
//        
//        for (int i = 0; i < flights.size(); i++) {
//			value += flights.get(i).toString();
//			if( i != (flights.size()-1))
//					value += ",";
//		}
//        value += "]";
//        return value;
//    }
	

}
