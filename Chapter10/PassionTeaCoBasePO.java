package com.framework.ux.utils.chapter10;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.testng.Assert.assertEquals;

/**
 * @author Carl Cocchiaro
 *
 * Passion Tea Company Base Page Object Class
 *
 */
public abstract class PassionTeaCoBasePO<M extends WebElement> {
    // local variables
    protected String pageTitle = "";

    // constructor
    public PassionTeaCoBasePO() throws Exception {
        PageFactory.initElements(CreateDriver.getInstance().getDriver(),this);
    }

    // elements
    @FindBy(css = "img[src*='01e56eb76d18b60c5fb3dcf451c080a1']")
    protected M passionTeaCoImg;

    @FindBy(css = "img[src*='ab7db4b80e0c0644f5f9226f2970739b']")
    protected M leafImg;

    @FindBy(css = "img[src*='cd390673d46bead889c368ae135a6ec2']")
    protected M organicImg;

    @FindBy(css = "a[href='welcome.html']")
    protected M welcome;

    @FindBy(css = "(//a[@href='menu.html'])[2]")
    protected M menu;

    @FindBy(css = "a[href='our-passion.html']")
    protected M ourPassion;

    @FindBy(css = "a[href='let-s-talk-tea.html']")
    protected M letsTalkTea;

    @FindBy(css = "a[href='check-out.html']")
    protected M checkOut;

    @FindBy(css = "//p[contains(text(),'Copyright')]")
    protected M copyright;

    // abstract methods

    protected abstract void setTitle(String pageTitle);
    protected abstract String getTitle();

    // common methods

    /**
     * verifyTitle method to verify page title
     *
     * @param title
     * @throws AssertionError
     */
    public void verifyTitle(String title) throws AssertionError {
        WebDriver driver = CreateDriver.getInstance().getDriver();

        assertEquals(driver.getTitle(), title, "Verify Page Title");
    }

    /**
     * navigate method to switch pages in app
     *
     * @param page
     * @throws Exception
     */
    public void navigate(String page) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        BrowserUtils.waitForClickable(By.xpath("//a[contains(text(),'" + page + "')]"), Global_VARS.TIMEOUT_MINUTE);
        driver.findElement(By.xpath("//a[contains(text(),'" + page + "')]")).click();

        // wait for page title
        BrowserUtils.waitFor(this.getTitle(), Global_VARS.TIMEOUT_ELEMENT);
    }

    /**
     * loadPage method to navigate to Target URL
     *
     * @param url
     * @param timeout
     * @throws Exception
     */
    public void loadPage(String url, int timeout) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        driver.navigate().to(url);

        // wait for page URL
        BrowserUtils.waitForURL(Global_VARS.TARGET_URL, timeout);
    }

    /**
     * verifyText method to verify page text
     *
     * @param pattern
     * @param text
     * @throws AssertionError
     */
    public void verifySpan(String pattern, String text) throws AssertionError {
        String getText = null;
        WebDriver driver = CreateDriver.getInstance().getDriver();

        getText = driver.findElement(By.xpath("//span[contains(text(),'" + pattern + "')]")).getText();
        assertEquals(getText, text, "Verify Span Text");
    }

    /**
     * verifyHeading method to verify page headings
     *
     * @param pattern
     * @param text
     * @throws AssertionError
     */
    public void verifyHeading(String pattern, String text) throws AssertionError {
        String getText = null;
        WebDriver driver = CreateDriver.getInstance().getDriver();

        getText = driver.findElement(By.xpath("//h1[contains(text(),'" + pattern + "')]")).getText();
        assertEquals(getText, text, "Verify Heading Text");
    }

    /**
     * verifyParagraph method to verify paragraph text
     *
     * @param pattern
     * @param text
     * @throws AssertionError
     */
    public void verifyParagraph(String pattern, String text) throws AssertionError {
        String getText = null;
        WebDriver driver = CreateDriver.getInstance().getDriver();

        getText = driver.findElement(By.xpath("//p[contains(text(),'" + pattern + "')]")).getText();
        assertEquals(getText, text, "Verify Paragraph Text");
    }

}
