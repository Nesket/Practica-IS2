package domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Movement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    private String eragiketa;
    private double kopurua;

    public Movement(User user, String eragiketa, double kopurua) {
        this.user = user;
        this.eragiketa = eragiketa;
        this.kopurua = kopurua;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUsername(User user) {
        this.user = user;
    }

    public String getEragiketa() {
        return eragiketa;
    }

    public void setEragiketa(String eragiketa) {
        this.eragiketa = eragiketa;
    }

    public double getKopurua() {
        return kopurua;
    }

    public void setKopurua(double kopurua) {
        this.kopurua = kopurua;
    }
}