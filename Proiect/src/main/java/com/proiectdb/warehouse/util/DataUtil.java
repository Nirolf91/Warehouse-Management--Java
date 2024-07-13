package main.java.com.proiectdb.warehouse.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

    // Formatul pentru datele de intrare în formatul "yyyy-MM-dd"
    private static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // Formatul pentru conversia la formatul "dd.MM.yyyy"
    private static final SimpleDateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static Date convertStringToDate(String dateString) {
        try {
            // Parsarea datei în formatul "yyyy-MM-dd"
            Date inputDate = INPUT_DATE_FORMAT.parse(dateString);

            // Conversia la formatul "dd.MM.yyyy"
            String outputDateString = OUTPUT_DATE_FORMAT.format(inputDate);

            // Parsarea din nou pentru a obține un obiect Date
            return OUTPUT_DATE_FORMAT.parse(outputDateString);
        } catch (ParseException e) {
            // Tratarea excepției de parsare a datelor
            System.err.println("Eroare la parsarea datei: " + e.getMessage());
            return null; // Sau puteți arunca o excepție, depinde de logica dvs.
        }
    }
}
