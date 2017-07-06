package ua.controlpay;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

public class JSONParser {
    UserAgent userAgent = null;
    String bankID = "7oiylpmiow8iy1smaze";

    JSONParser() {
        userAgent = new UserAgent();
        try {
            userAgent.sendGET("http://resources.finance.ua/ru/public/currency-cash.json");
        } catch (ResponseException e) {
            System.err.println(e);
        }
    }

    protected float getExchangeRates() {
        try {
            return Float.parseFloat(userAgent.json.findFirst("organizations").findFirst("{'id':'"+bankID+"'}").findFirst("currencies").findFirst("USD").get("ask").toString());
        } catch (NotFound notFound) {
            System.err.println(notFound);
            return 0.0f;
        }
    }

    /*public static void main(String[] args) {
        UserAgent userAgent = new UserAgent();
        try {
            userAgent.sendGET("http://resources.finance.ua/ru/public/currency-cash.json");
            System.out.println(Float.parseFloat(userAgent.json.findFirst("organizations").findFirst("{'id':'7oiylpmiow8iy1smaze'}").findFirst("currencies").findFirst("USD").get("ask").toString()));
        } catch (ResponseException e) {
            System.err.println(e);
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }
    }*/
}
