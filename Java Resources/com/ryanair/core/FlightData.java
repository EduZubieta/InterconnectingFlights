package com.ryanair.core;

import java.util.List;

public class FlightData {

	private int stops;
	private List<LegData> legs;
	
	public FlightData() {}
	
	public FlightData(int stops, List<LegData> legs) {
		this.stops = stops;
		this.legs = legs;
	}
	public int getStops() {
		return stops;
	}
	public void setStops(int stops) {
		this.stops = stops;
	}
	public List<LegData> getLegs() {
		return legs;
	}
	public void setLegs(List<LegData> legs) {
		this.legs = legs;
	}
	
	
}
