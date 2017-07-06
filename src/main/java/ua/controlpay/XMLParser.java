package ua.controlpay;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

public class XMLParser {
    UserAgent userAgent = null;

    XMLParser() {
        userAgent = new UserAgent();
        try {
            userAgent.sendGET("http://resources.finance.ua/ru/public/currency-cash.json");
        } catch (ResponseException e) {
            System.err.println(e);
        }
    }

    protected float getExchangeRates() {
        try {
            return Float.parseFloat(userAgent.json.findFirst("organizations").findFirst("{'id':'7oiylpmiow8iy1sma7w'}").findFirst("currencies").findFirst("USD").get("ask").toString());
        } catch (NotFound notFound) {
            System.err.println(notFound);
            return 0.0f;
        }
    }
}
