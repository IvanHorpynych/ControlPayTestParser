package ua.controlpay;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engine {

    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();
        float currentExchangeRates = jsonParser.getExchangeRates();
        Scanner in = new Scanner(System.in);
        String kindParser;
        System.out.println("Welcome!");
        while (true) {
            System.out.print("1 - Jaunt Parser;\n2 - Selenium Parser;\nq - Exit;\nChoose kind of parser:");
            if (in.hasNext()) {
                String buf = in.nextLine().trim();
                Pattern p = Pattern.compile("^([1|2|q])$");
                Matcher m = p.matcher(buf);
                if (m.matches() && !buf.equals("q")) {
                    kindParser = buf;
                    while (true) {
                        System.out.print("Write article of product:");
                        if (in.hasNextLine()) {
                            String article = in.nextLine().trim();
                            HashMap<String, Integer> fields = null;
                            switch (kindParser) {
                                case "1":
                                    fields = new JauntParser().parser(article);
                                    break;
                                case "2":
                                    fields = new SeleniumParser().parser(article);
                                    break;
                                case "q":
                                    System.exit(0);
                                    break;
                                default:
                                    System.out.println("Invalid kind! Try again.");
                                    continue;
                            } if(!(fields==null)){
                            System.out.println("Products: " + article);
                            System.out.println("Price in UAH: " + fields.get("price"));
                            System.out.println("Price in USD: " + (fields.get("price") / currentExchangeRates));
                            if (fields.get("reviews") > 49) {
                                System.out.println("Reviews more than 49!");
                            } else if (fields.get("reviews") < 49) {
                                System.out.println("Reviews less than 49!");
                            } else {
                                System.out.println("Reviews exactly 49!");
                            }
                            System.out.println("Stars: " + fields.get("stars")+"\n");
                            break;
                            } else {
                                System.out.println("You have problem with article! Try again!");
                            }
                        } else {
                            System.out.println("Invalid article! try again!");
                        }
                    }
                } else if(buf.equals("q")) {
                    break;
                } else {
                    System.out.println("Invalid kind! Try again.");
                }
            } else {
                System.out.println("Invalid kind! Try again.");
            }

        }
    }
}
