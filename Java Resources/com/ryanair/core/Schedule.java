package com.ryanair.core;

import java.util.List;

public class Schedule {
	private int month;
	private List<Day> days;
	
	public Schedule() {}
	
	public Schedule(int month, List<Day> days) {
		this.month = month;
		this.days = days;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}
}
