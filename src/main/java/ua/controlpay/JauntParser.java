package ua.controlpay;

import com.jaunt.Element;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import com.jaunt.component.Form;

import java.util.HashMap;

public class JauntParser {
    protected HashMap<String,Integer> parser(String article){
        try{
            UserAgent userAgent = new UserAgent();
            userAgent.visit("http://rozetka.com.ua");
            Form form = userAgent.doc.getForm(0);
            form.setTextField("text", article);
            form.submit();

            Element price = userAgent.doc.findEvery("<meta itemprop=\"price\">");
            int priceValue = Integer.parseInt(price.innerHTML().toString().substring(price.innerHTML().toString().lastIndexOf("/")+1,price.innerHTML().toString().lastIndexOf("\"")));

            Element reviews = userAgent.doc.findEvery("<span name=\"count_comments\">");
            int reviewsValue =Integer.parseInt(reviews.innerText().trim());

            Element stars = userAgent.doc.findEvery("<span class=\"g-rating-stars-i-medium\">");
            int starsValue =Integer.parseInt(stars.innerHTML().toString().substring(stars.innerHTML().toString().lastIndexOf("width:")+6,stars.innerHTML().toString().lastIndexOf("%\"")))/20;
            return new HashMap<String, Integer>() {{
                put("price",  priceValue);
                put("reviews", reviewsValue);
                put("stars",   starsValue);
            }};
        }
        catch(JauntException e){
            System.out.println("You have problem with article! Try again!");
            return null;
        }
    }
}
