package com.automation.testautomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://demoqa.com/checkbox");
        System.out.println("browser is opened with: "+driver.getCurrentUrl());

        // Find the full name text box
        WebElement homeCheckbox = driver.findElement(By.xpath("//span[text()='Home']/parent::*/preceding-sibling::span[1]"));
        System.out.println("wordFileCheckbox is found?: "+((homeCheckbox==null)?"false":"true"));

        // enter the text into the text box
        if(homeCheckbox != null){
            homeCheckbox.click();

            if(homeCheckbox.getAttribute("aria-checked")!=null){
                String checked = homeCheckbox.getAttribute("aria-checked");
                if(checked.equals("true")){
                    System.out.println("Successfully checked the box");
                }else{
                    System.err.println("Failed to check the box");
                }
            }
        }
    }
}