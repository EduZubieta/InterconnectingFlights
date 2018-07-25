package com.ryanair.core;

import java.time.LocalDateTime;

public class LegData {

	private String departureAirport;
	private String arrivalAirport;
	private LocalDateTime departureDateTime;
	private LocalDateTime arrivalDateTime;
	
	public LegData(){}
	
	public LegData(String departureAirport, String arrivalAirport, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime){
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(LocalDateTime departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}
	public int getArrivalHour() {
		return arrivalDateTime.getHour();
	}
	public int getArrivalMinute() {
		return arrivalDateTime.getMinute();
	}
	public int getDepartureHour() {
		return departureDateTime.getHour();
	}
	public int getDepartureMinute() {
		return departureDateTime.getMinute();
	}
}
