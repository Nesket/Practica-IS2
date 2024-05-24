package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Discount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String kodea;
	private double portzentaia;
	private boolean active;

	public Discount(String kodea, double portzentaia, boolean active) {
		this.kodea = kodea;
		this.portzentaia = portzentaia;
		this.active = active;
	}

	public String getKodea() {
		return kodea;
	}

	public void setKodea(String kodea) {
		this.kodea = kodea;
	}

	public double getPortzentaia() {
		return portzentaia;
	}

	public void setPortzentaia(double portzentaia) {
		this.portzentaia = portzentaia;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
