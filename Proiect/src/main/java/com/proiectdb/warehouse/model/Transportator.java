package main.java.com.proiectdb.warehouse.model;

public class Transportator {
    private int idTransportator;
    private String nume;
    private String contact;
    private double pretPeKg;

    public int getIdTransportator() {
        return idTransportator;
    }

    public void setIdTransportator(int idTransportator) {
        this.idTransportator = idTransportator;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getPretPeKg() {
        return pretPeKg;
    }

    public void setPretPeKg(double pretPeKg) {
        this.pretPeKg = pretPeKg;
    }
}
