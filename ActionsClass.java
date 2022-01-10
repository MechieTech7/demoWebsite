package com.demoSite;

import org.openqa.selenium.WebElement;

public class ActionsClass {

        public boolean isElementPresent(WebElement element) {
            if (element.isDisplayed()) {
                return true;
            }else{
                return false;
            }
        }
        public String enterValues(WebElement element , String textValue){
            if (isElementPresent(element)){
                element.sendKeys(textValue);
            }else {
                System.out.println("Web element is not present");
            }
            return textValue;
        }
        public void clickButton(WebElement element){
            element.click();
        }
        public String pageTitle(){
            return ClothesSite.driver.getTitle();
        }
    }

