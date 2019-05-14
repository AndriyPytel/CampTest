package back;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static back.Parser.DATA_URL;

public class Model {

    private String name;
    private Date date;
    private double amount;
    private String currency;
    private static Map<String, Double> currencyMap = null;

    public Model(Date date, double amount,String currency, String name) throws IOException {
        setDate(date);
        setAmount(amount);
        setCurrency(currency);
        setName(name);
    }

    private void setDate(Date date) {
        this.date = date;
    }

    private void setAmount(double amount) {
        this.amount = amount;
    }

    private void setCurrency(String currency) throws IOException {
        if (getCurrencyMap().get(currency) != null) {
            this.currency = currency;
        } else {
            throw new IOException();
        }
    }

     private void setName(String name) {
        this.name = name;
    }

    Date getDate() {
        return date;
    }

    double getAmount() {
        return amount;
    }

    String getCurrency() {
        return currency;
    }

    String getName() {
        return name;
    }

    String getAll() {
        return getName() + " " + getAmount() + " " + getCurrency();
    }

    static Map<String, Double> getCurrencyMap() throws IOException {
        if (currencyMap == null) {
            currencyMap = Parser.getRates(Parser.getJsonFromUrl(DATA_URL));
        }
        return currencyMap;
    }

}