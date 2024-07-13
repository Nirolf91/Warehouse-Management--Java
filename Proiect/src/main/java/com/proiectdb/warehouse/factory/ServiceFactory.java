package main.java.com.proiectdb.warehouse.factory;

import main.java.com.proiectdb.warehouse.model.Comanda;
import main.java.com.proiectdb.warehouse.service.*;

public class ServiceFactory {

    public static AngajatService getAngajatService() {
        return new AngajatService();
    }
    public static ClientService getClientService() {
        return new ClientService();
    }
    public static ComandaService getComandaService() {
        return new ComandaService();
    }
    public static EvaluareService getEvaluareService() {
        return new EvaluareService();
    }
    public static FurnizorService getFurnizorService() {
        return new FurnizorService();
    }
    public static MaterialService getMaterialService() {
        return new MaterialService();
    }
    public static TransportatorService getTransportatorService() {
        return new TransportatorService();
    }
}
