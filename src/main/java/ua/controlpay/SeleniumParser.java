package ua.controlpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;

public class SeleniumParser {

    WebDriver webDriver = null;

    SeleniumParser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
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
        setSearchAtribute(article);
        WebElement priceElement = webDriver.findElement(By.id("price_label"));
        int priceValue = Integer.parseInt(priceElement.getText());

        WebElement reviewsElement = webDriver.findElement(By.className("g-rating-stars-i-medium"));
        int reviewsValue = (int) (((float) reviewsElement.getSize().getWidth() / webDriver.findElement(By.className("g-rating-stars-medium")).getSize().getWidth()) * 10 / 2);

        WebElement starsElement = webDriver.findElement(By.name("count_comments"));
        int starsValue = Integer.parseInt(starsElement.getText());

        return new HashMap<String, Integer>() {{
            put("price", priceValue);
            put("reviews", reviewsValue);
            put("stars", starsValue);
        }};
    }

}
