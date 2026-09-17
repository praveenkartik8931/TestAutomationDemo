package com.automation.testautomation;

import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestW3Schools {
    ChromeDriver driver;
    WebDriverWait wait;
    FluentWait<WebDriver> fluentWait;
    JavascriptExecutor jsExecutor;

    @Before
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);

        driver.get("https://www.w3schools.com/html/html_iframe.asp");
        System.out.println("browser is opened with: "+driver.getCurrentUrl());

        //configure implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Explicit wait object creation
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Fluent wait object creation
        fluentWait = new FluentWait<WebDriver>(driver)
                        .withTimeout(Duration.ofSeconds(30))
                        .pollingEvery(Duration.ofSeconds(5))
                        .ignoring(TimeoutException.class)
                        .withMessage("Element not found.. will wait for 5 seconds before next attempt");

        // Javascript execution object initialisation
        jsExecutor = (JavascriptExecutor)driver;
    }
    
    @Test
    public void testIFrame(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[.='HTML Iframes']")));
        WebElement iFrame = driver.findElement(By.xpath("//iframe[@title='W3Schools HTML Tutorial']"));
        // Switch to our frame
        driver.switchTo().frame(iFrame);
        WebElement seeAllExamples = driver.findElement(By.xpath("//a[.='See all HTML Exercises']"));
        seeAllExamples.click();
        // switch back to default content
        driver.switchTo().defaultContent();
        WebElement nextPage = driver.findElement(By.xpath("//h1[.='HTML Iframes']/following-sibling::*[1]//a[contains(text(),'Next')]"));
        nextPage.click();
        // Wait till next page is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[.='HTML Iframes Exercises']")));
        Assert.assertTrue(driver.findElement(By.xpath("//h1[.='HTML Iframes Exercises']")).isDisplayed());
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
