package dataAccess;

import java.util.Date;

import domain.Booking;

public class ErreklamazioaBidaliParameter {
	private String nor;
	private String nori;
	private Date gaur;
	private Booking booking;
	private String textua;
	private boolean aurk;

	public ErreklamazioaBidaliParameter(String nor, String nori, Date gaur, Booking booking, String textua,
			boolean aurk) {
		this.nor = nor;
		this.nori = nori;
		this.gaur = gaur;
		this.booking = booking;
		this.textua = textua;
		this.aurk = aurk;
	}

	public String getNor() {
		return nor;
	}

	public void setNor(String nor) {
		this.nor = nor;
	}

	public String getNori() {
		return nori;
	}

	public void setNori(String nori) {
		this.nori = nori;
	}

	public Date getGaur() {
		return gaur;
	}

	public void setGaur(Date gaur) {
		this.gaur = gaur;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public String getTextua() {
		return textua;
	}

	public void setTextua(String textua) {
		this.textua = textua;
	}

	public boolean isAurk() {
		return aurk;
	}

	public void setAurk(boolean aurk) {
		this.aurk = aurk;
	}
}