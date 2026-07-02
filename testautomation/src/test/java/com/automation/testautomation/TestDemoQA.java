package com.automation.testautomation;

import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestDemoQA {
    
    ChromeDriver driver;

    @Before
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);

        driver.get("https://demoqa.com/checkbox");
        System.out.println("browser is opened with: "+driver.getCurrentUrl());

        //configure implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    @Ignore
    public void testTextEntry(){

    }

    @Test
    public void testElementClick(){
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
                Assert.assertEquals("true", checked);
            }
        }
    }

    @Test
    @Ignore
    public void testGetAttribute(){

    }

    @After
    public void tearDown(){
        // Closes the active window handle from selenium
        driver.close();
        // Closes the active windows handle and
        // Quits the complete session
        driver.quit();
    }
}
