package com.automation.testautomation;

import java.time.Duration;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestWindowHandles {
    ChromeDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);

        driver.get("https://demoqa.com/browser-windows");
        System.out.println("browser is opened with: "+driver.getCurrentUrl());

        //configure implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //Explicit wait object creation
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void testNewTab(){
        //Wait for the page to load and ensure the 'New Tab' button is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tabButton")));
        WebElement newTabButton = driver.findElement(By.id("tabButton"));
        // Get the current window handle
        String originalHandle = driver.getWindowHandle();
        String currentWindowHandle = originalHandle;
        // Click on 'New Tab' button
        newTabButton.click();
        // Get the total windows open in the driver session
        Set<String> windowHandles = driver.getWindowHandles();
        // Iterate over the handles and switch to the new tab
        for(String handle: windowHandles){
            if(!currentWindowHandle.equals(handle)){
                // switch to the new window
                driver.switchTo().window(handle);
                currentWindowHandle = handle;
                break;
            }
        }
        WebElement sampleHeading = driver.findElement(By.id("sampleHeading"));
        // Check if the sample heading matches 'This is a sample page'
        Assert.assertEquals("This is a sample page", sampleHeading.getText());
        // Switch back to the original window
        driver.switchTo().window(originalHandle);
        // Find the 'New Tab' button
        //Wait for the page to load and ensure the 'New Tab' button is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tabButton")));
        newTabButton = driver.findElement(By.id("tabButton"));
        Assert.assertTrue(newTabButton!=null && newTabButton.isDisplayed() && newTabButton.isEnabled());
    }
    
    @Test
    public void testNewWindow(){
        //Wait for the page to load and ensure the 'New Tab' button is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tabButton")));
        WebElement newTabButton = driver.findElement(By.id("tabButton"));
        // Get the current window handle
        String originalHandle = driver.getWindowHandle();
        String currentWindowHandle = originalHandle;
        // Click on 'New Window' button
        newTabButton.click();
        // Get the total windows open in the driver session
        Set<String> windowHandles = driver.getWindowHandles();
        // Iterate over the handles and switch to the new tab
        for(String handle: windowHandles){
            if(!currentWindowHandle.equals(handle)){
                // switch to the new window
                driver.switchTo().window(handle);
                currentWindowHandle = handle;
                break;
            }
        }
        WebElement sampleHeading = driver.findElement(By.id("sampleHeading"));
        // Check if the sample heading matches 'This is a sample page'
        Assert.assertEquals("This is a sample page", sampleHeading.getText());
        // Close the current tab
        driver.close();
        // Switch back to the original window
        driver.switchTo().window(originalHandle);
        currentWindowHandle = originalHandle;
        // New window operations
        WebElement newWindowButton = driver.findElement(By.id("windowButton"));
        // Click on 'New Window' button
        newWindowButton.click();
        // Get the total windows open in the driver session
        windowHandles = driver.getWindowHandles();
        // Iterate over the handles and switch to the new tab
        for(String handle: windowHandles){
            if(!currentWindowHandle.equals(handle)){
                // switch to the new window
                driver.switchTo().window(handle);
                currentWindowHandle = handle;
                break;
            }
        }
        sampleHeading = driver.findElement(By.id("sampleHeading"));
        // Check if the sample heading matches 'This is a sample page'
        Assert.assertEquals("This is a sample page", sampleHeading.getText());
        // Close the current window
        driver.close();
        // Switch back to the original window
        driver.switchTo().window(originalHandle);
        // Find the 'New Window' button
        //Wait for the page to load and ensure the 'New Tab' button is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        newTabButton = driver.findElement(By.id("windowButton"));
        Assert.assertTrue(newTabButton!=null && newTabButton.isDisplayed() && newTabButton.isEnabled());
    }

    @Test
    public void testMultipleWindowHandlingWithDifferentMessage(){

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
