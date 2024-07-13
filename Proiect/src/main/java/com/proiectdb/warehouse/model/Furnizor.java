package main.java.com.proiectdb.warehouse.model;

public class Furnizor {
    private int idFurnizor;
    private String nume;
    private String adresa;
    private String contact;

    public int getIdFurnizor() {
        return idFurnizor;
    }

    public void setIdFurnizor(int idFurnizor) {
        this.idFurnizor = idFurnizor;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
