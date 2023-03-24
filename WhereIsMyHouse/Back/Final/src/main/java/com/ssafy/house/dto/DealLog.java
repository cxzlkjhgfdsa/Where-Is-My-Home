package com.ssafy.house.dto;

public class DealLog {
	private String aptCode, year, price;

	public String getAptCode() {
		return aptCode;
	}

	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "DealLog [aptCode=" + aptCode + ", year=" + year + ", price=" + price + "]";
	}

	
	

}
