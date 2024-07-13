package main.java.com.proiectdb.warehouse.model;

public class Material {
    private int idMaterial;
    private String nume;
    private String descriere;
    private int cantitateInStoc;
    private double pretUnitar;
    private int idFurnizor;

    public int getIdMaterial() {
        return idMaterial;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getCantitateInStoc() {
        return cantitateInStoc;
    }

    public void setCantitateInStoc(int cantitateInStoc) {
        this.cantitateInStoc = cantitateInStoc;
    }

    public double getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(double pretUnitar) {
        this.pretUnitar = pretUnitar;
    }

    public int getIdFurnizor() {
        return idFurnizor;
    }

    public void setIdFurnizor(int idFurnizor) {
        this.idFurnizor = idFurnizor;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }
}
