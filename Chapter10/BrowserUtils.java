package com.framework.ux.utils.chapter10;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Carl Cocchiaro
 *
 * Browser Utility Class
 *
 */
public class BrowserUtils {

    /**
     * waitFor method to poll page title
     *
     * @param title
     * @param timer
     * @throws Exception
     */

    public static void waitFor(String title,
                               int timer)
                               throws Exception {

        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebDriverWait exists = new WebDriverWait(driver, timer);

        exists.until( ExpectedConditions.refreshed(ExpectedConditions.titleContains(title)) );
    }

    /**
     * waitForURL method to poll page URL
     *
     * @param url
     * @param timer
     * @throws Exception
     */
    public static void waitForURL(String url,
                                  int timer)
                                  throws Exception {

        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebDriverWait exists = new WebDriverWait(driver, timer);

        exists.until( ExpectedConditions.refreshed(ExpectedConditions.urlContains(url)) );
    }

    /**
     * waitForClickable method to poll for clickable
     *
     * @param by
     * @param timer
     * @throws Exception
     */
    public static void waitForClickable(By by,
                                        int timer)
                                        throws Exception {

        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebDriverWait exists = new WebDriverWait(driver, timer);

        exists.until( ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)) );
    }

    /**
     * click method using JavaScript API click
     *
     * @param by
     * @throws Exception
     */
    public static void click(By by) throws Exception {

        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebElement element = driver.findElement(by);

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", element);
    }

}
