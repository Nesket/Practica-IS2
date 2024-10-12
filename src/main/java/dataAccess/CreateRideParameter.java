package dataAccess;

import java.util.Date;

public class CreateRideParameter {
	private String from;
	private String to;
	private Date date;
	private int nPlaces;
	private float price;
	private String driverName;

	public CreateRideParameter(String from, String to, Date date, int nPlaces, float price, String driverName) {
		this.from = from;
		this.to = to;
		this.date = date;
		this.nPlaces = nPlaces;
		this.price = price;
		this.driverName = driverName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getnPlaces() {
		return nPlaces;
	}

	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
}