package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlID
	@Id
	private String username;
	private String passwd;
	private String mota;
	private double money;
	private double balorazioa;
	private double izoztatutakoDirua;
	private int balkop;
	private int erreklamaKop;

	public User(String username, String passwd, String mota) {
		this.username = username;
		this.passwd = passwd;
		this.mota = mota;
		this.money = 0;
		this.balorazioa = 0;
		this.izoztatutakoDirua = 0;
		this.balkop = 0;
		this.erreklamaKop = 0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public String getPassword() {
		return passwd;
	}

	public void setPassword(String name) {
		this.passwd = name;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getBalorazioa() {
		return balorazioa;
	}

	public void setBalorazioa(double balorazioa) {
		this.balorazioa = balorazioa;
	}

	public double getIzoztatutakoDirua() {
		return izoztatutakoDirua;
	}

	public void setIzoztatutakoDirua(double izoztatutakoDirua) {
		this.izoztatutakoDirua = izoztatutakoDirua;
	}

	public String toString() {
		return username + ";" + passwd;
	}

	public int getBalkop() {
		return balkop;
	}

	public void setBalkop(int balkop) {
		this.balkop = balkop;
	}

	public int getErreklamaKop() {
		return erreklamaKop;
	}

	public void setErreklamaKop(int erreklamaKop) {
		this.erreklamaKop = erreklamaKop;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username != other.username)
			return false;
		return true;
	}

}
