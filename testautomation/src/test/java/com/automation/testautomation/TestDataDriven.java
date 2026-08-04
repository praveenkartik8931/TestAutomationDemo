package com.automation.testautomation;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(value=Parameterized.class)
public class TestDataDriven {
ChromeDriver driver;
    WebDriverWait wait;
    FluentWait<WebDriver> fluentWait;
    JavascriptExecutor jsExecutor;
    
    private String price;
    private String hotel;

    public TestDataDriven(String price, String hotel){
        this.price = price;
        this.hotel = hotel;
    }

    @Parameters
    public static Collection testData(){
        return Arrays.asList(
            new Object[][]{
                {"₹4,668", "Bloom Hotel - Karol Bagh"},
                {"₹5,611", "Bloom Hotel - CR Park"}
            }
        );
    }    

    @Before
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);

        driver.get("https://www.ixigo.com/");
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

    public void handleOnboardingPopup(){
        WebElement popupImage = null;
        try{
            fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("popupImage")));
            popupImage = driver.findElement(By.id("popupImage"));
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        if(popupImage!=null && popupImage.isDisplayed() && popupImage.isEnabled()){
            driver.switchTo().frame(driver.findElement(By.id("wiz-iframe-intent")));
            // click the popup close
            WebElement closeButton = driver.findElement(By.id("closeButton"));
            closeButton.click();
            // switch to default content
            driver.switchTo().defaultContent();
        }else{
            System.out.println("No popup is displayed.. so skipping this step");
        }
    }

    public void navigateToHotelsPage(){
        // Wait until page loads to show the tabs
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html/body/main/div[2]/div[1]/div[2]/div/ul/li[2]/a")));
        // Find the full name text box
        WebElement hotels = driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[2]/div/ul/li[2]/a"));
        // click on hotels
        hotels.click();
    }

    @Test
    public void testElementWait(){
        handleOnboardingPopup();
        navigateToHotelsPage();
        // wait until test data hotel image is visible
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(String.format("//*[@alt='%s']",this.hotel))));
        // Find element for price of the hotel
        WebElement hotelPrice = driver.findElement(By.xpath(String.format("//*[@alt='%s']/../following-sibling::div//div[contains(@class,'body')]",this.hotel)));
        // Get the price from the element
        String price = hotelPrice.getText();
        // Verify if the price is as per the test data rupees
        Assert.assertEquals(this.price, price);
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
