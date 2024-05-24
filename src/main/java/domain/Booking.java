package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlIDREF;

import java.io.Serializable;

@Entity
@TableGenerator(name = "BookingGen", initialValue = 0, allocationSize = 1)
public class Booking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BookingGen")
	private int bookNumber;
	@ManyToOne
	private Ride ride;
	@ManyToOne
	private Traveler traveler;
	private int seats;
	private String status;
	private double deskontua;
	@XmlIDREF
	@OneToMany(mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Complaint complaint;

	public Booking(Ride ride, Traveler traveler, int seats) {
		this.ride = ride;
		this.traveler = traveler;
		this.seats = seats;
		this.status = "NotDefined";
		this.deskontua = 0;
		this.complaint = null;
	}

	public int getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getDeskontua() {
		return deskontua;
	}

	public void setDeskontua(double deskontua) {
		this.deskontua = deskontua;
	}

	public double prezioaKalkulatu() {
		return (this.ride.getPrice() - this.deskontua)*this.seats;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}

}
