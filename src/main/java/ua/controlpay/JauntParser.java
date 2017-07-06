package ua.controlpay;

import com.jaunt.Element;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import com.jaunt.component.Form;

import java.util.HashMap;

public class JauntParser {
    int priceValue = 0;
    int reviewsValue = 0;
    int starsValue = 0;
    protected HashMap<String,Integer> parser(String article){
        try{
            UserAgent userAgent = new UserAgent();
            userAgent.visit("http://rozetka.com.ua");
            Form form = userAgent.doc.getForm(0);
            form.setTextField("text", article);
            form.submit();

            String buf;
            Element price = userAgent.doc.findEvery("<meta itemprop=\"price\">");
            buf = price.innerHTML().toString().substring(price.innerHTML().toString().lastIndexOf("/")+1,price.innerHTML().toString().lastIndexOf("\""));
            if(!buf.equals("")) priceValue = Integer.parseInt(buf);


            Element reviews = userAgent.doc.findEvery("<span name=\"count_comments\">");
            buf = reviews.innerText().trim();
            if(!buf.equals(""))reviewsValue =Integer.parseInt(buf);

            Element stars = userAgent.doc.findEvery("<span class=\"g-rating-stars-i-medium\">");
            buf = stars.innerHTML().toString().substring(stars.innerHTML().toString().lastIndexOf("width:")+6,stars.innerHTML().toString().lastIndexOf("%\""));
            if(!buf.equals("")) starsValue =Integer.parseInt(buf)/20;
            return new HashMap<String, Integer>() {{
                put("price",  priceValue);
                put("reviews", reviewsValue);
                put("stars",   starsValue);
            }};
        }
        catch(JauntException e){
            return null;
        } catch (StringIndexOutOfBoundsException e2){
            return null;
        }
    }
}
