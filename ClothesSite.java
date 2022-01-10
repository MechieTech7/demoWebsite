package com.demoSite;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ClothesSite {

    public static WebDriver driver;
    ActionsClass actionsClass = new ActionsClass();

    String userWorkingDirectory = System.getProperty("user.dir");
    String pathSeparator = System.getProperty("file.separator");
    String details = userWorkingDirectory + pathSeparator + "src" + pathSeparator + "test" + pathSeparator + "java" + pathSeparator + "com" + pathSeparator + "demoSite" + pathSeparator + "details.properties";


    @BeforeTest
    public void LaunchBrowser() throws IOException {

        FileInputStream inputStream = new FileInputStream(details);
        Properties properties = new Properties();
        properties.load(inputStream);
        String browser = properties.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
    }


    @Test(priority = 0)
    public void OpenWebsite() throws IOException {


            FileInputStream inputStream = new FileInputStream(details);
            Properties properties = new Properties();
            properties.load(inputStream);
            String path = properties.getProperty("Url");

            driver.get(path);
            WebElement searchBox = driver.findElement(By.id(properties.getProperty("searchboxid")));
            WebElement searchButton = driver.findElement(By.name(properties.getProperty("searchbutton")));


            System.out.println(actionsClass.pageTitle());
            actionsClass.isElementPresent(searchBox);
            String dressList = actionsClass.enterValues(searchBox,properties.getProperty("text"));
            actionsClass.isElementPresent(searchButton);
            actionsClass.clickButton(searchButton);

            List<WebElement> searchedClothes = driver.findElements(By.xpath(properties.getProperty("xPath")));
            for (WebElement webElement: searchedClothes) {
            System.out.println( webElement.getText());
            }

            List<WebElement> clothesPrice = driver.findElements(By.xpath(properties.getProperty("costxPath")));
            for (WebElement webElement: clothesPrice) {
            System.out.println(webElement.getText());
            }
            Map<Integer,String> productPrice = new LinkedHashMap<>();
             for (int count =0;count< searchedClothes.size();count++){
            String name = searchedClothes.get(count).getText();
            String prices = clothesPrice.get(count).getText();
            prices= prices.replace("$","");
            int num = Integer.parseInt(prices.replace(".",""));
            productPrice.put(num,name);
        }
        Set<Integer> set = productPrice.keySet();
        ArrayList<Integer> list = new ArrayList<Integer>(set);
        Collections.sort(list);
        int low_price = list.get(0);
        System.out.println(productPrice.get(low_price));
        System.out.println(low_price);

        if(dressList.contains(properties.getProperty("partialtextmatch"))){
                System.out.println("verified");
            }else {
                System.out.println("Not matched");
            }
}


    @AfterTest
    public void QuitBrowser(){
        driver.quit();
    }

}



