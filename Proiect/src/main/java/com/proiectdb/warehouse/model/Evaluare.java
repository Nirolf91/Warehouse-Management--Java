package main.java.com.proiectdb.warehouse.model;

import java.util.Date;

public class Evaluare extends Material {
    private int idEvaluare;
    private int idClient;
    private int scor;
    private String feedback;
    private Date dataEvaluarii;

    public int getIdEvaluare() {
        return idEvaluare;
    }

    public void setIdEvaluare(int idEvaluare) {
        this.idEvaluare = idEvaluare;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getScor() {
        return scor;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getDataEvaluarii() {
        return dataEvaluarii;
    }

    public void setDataEvaluarii(Date dataEvaluarii) {
        this.dataEvaluarii = dataEvaluarii;
    }
}
