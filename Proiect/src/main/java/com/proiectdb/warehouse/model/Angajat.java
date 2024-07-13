package main.java.com.proiectdb.warehouse.model;

public class Angajat {
    private int idAngajat;
    private String nume;
    private String functie;
    private String dateDeContact;

    public int getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(int idAngajat) {
        this.idAngajat = idAngajat;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getDateDeContact() {
        return dateDeContact;
    }

    public void setDateDeContact(String dateDeContact) {
        this.dateDeContact = dateDeContact;
    }
}
