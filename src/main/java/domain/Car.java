package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Car implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String matrikula;
	private String modeloa;
	private int eserlekuak;
	@ManyToOne
	private Driver driver;

	public Car(String matrikula, String modeloa, int eserlekuak) {
		super();
		this.matrikula = matrikula;
		this.modeloa = modeloa;
		this.eserlekuak = eserlekuak;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public String getMatrikula() {
		return matrikula;
	}

	public void setMatrikula(String matrikula) {
		this.matrikula = matrikula;
	}

	public String getModeloa() {
		return modeloa;
	}

	public void setModeloa(String modeloa) {
		this.modeloa = modeloa;
	}

	public int getEserlekuak() {
		return eserlekuak;
	}

	public void setEserlekuak(int eserlekuak) {
		this.eserlekuak = eserlekuak;
	}

}
