package main.java.com.proiectdb.warehouse.model;

import java.util.Date;

public class Comanda {
    private int idComanda;
    private Date dataComenzii;
    private int idClient;
    private int idFurnizor;
    private int idAngajat;
    private int idMaterial;
    private double totalComanda;
    private String statutComanda;
    private String tipComanda;
    private int cantitate;
    private double pretTotal;

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public Date getDataComenzii() {
        return dataComenzii;
    }

    public void setDataComenzii(Date dataComenzii) {
        this.dataComenzii = dataComenzii;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdFurnizor() {
        return idFurnizor;
    }

    public void setIdFurnizor(int idFurnizor) {
        this.idFurnizor = idFurnizor;
    }

    public int getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(int idAngajat) {
        this.idAngajat = idAngajat;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public double getTotalComanda() {
        return totalComanda;
    }

    public void setTotalComanda(double totalComanda) {
        this.totalComanda = totalComanda;
    }

    public String getStatutComanda() {
        return statutComanda;
    }

    public void setStatutComanda(String statutComanda) {
        this.statutComanda = statutComanda;
    }

    public String getTipComanda() {
        return tipComanda;
    }

    public void setTipComanda(String tipComanda) {
        this.tipComanda = tipComanda;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public double getPretTotal() {
        return pretTotal;
    }

    public void setPretTotal(double pretTotal) {
        this.pretTotal = pretTotal;
    }
}
