package ua.controlpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URL;
import java.util.HashMap;

public class SeleniumParser {

    WebDriver webDriver = null;
    int priceValue;
    int starsValue;
    int reviewsValue;

    SeleniumParser() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window();
        webDriver.get("http://rozetka.com.ua");
    }

    private void setSearchAtribute(String article) {
        WebElement searchField = webDriver.findElement(By.name("text"));
        searchField.clear();
        searchField.sendKeys(article);
        WebElement searchButton = webDriver.findElement(By.name("rz-search-button"));
        searchButton.click();

    }

    protected HashMap<String, Integer> parser(String article) {

        try {
            setSearchAtribute(article);
            WebElement priceElement = webDriver.findElement(By.id("price_label"));
            priceValue = Integer.parseInt(priceElement.getText());
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }

        try {
            WebElement starsElement = webDriver.findElement(By.className("g-rating-stars-i-medium"));
            starsValue = (int) (((float) starsElement.getSize().getWidth() / webDriver.findElement(By.className("g-rating-stars-medium")).getSize().getWidth()) * 10 / 2);
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }

        try {
            if (!webDriver.findElement(By.name("count_comments")).equals(null)) {
                WebElement reviewsElement = webDriver.findElement(By.name("count_comments"));
                reviewsValue = Integer.parseInt(reviewsElement.getText());
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }

        if (priceValue == 0 && reviewsValue == 0 && starsValue == 0) {
            new org.openqa.selenium.NoSuchElementException("Error!");
            webDriver.quit();
            return null;
        }
        webDriver.quit();
        return new HashMap<String, Integer>() {{
            put("price", priceValue);
            put("reviews", reviewsValue);
            put("stars", starsValue);
        }};
    }

}


