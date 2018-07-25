package com.ryanair.core;

import java.time.LocalDateTime;

public class Flight {
	
	private int number;
	private LocalDateTime departureDateTime;
	private LocalDateTime arrivalDateTime;
	
	public Flight(){}
	
	public Flight(int number, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
		this.number = number;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
