package com.framework.ux.utils.chapter10;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import static org.testng.Assert.assertEquals;

/**
 * @author Carl Cocchiaro
 *
 * Passion Tea Company Welcome Sub-class Page Object Class
 *
 */
public class PassionTeaCoWelcomePO<M extends WebElement> extends PassionTeaCoBasePO<M> {
    // local variables
    private static final String WELCOME_TITLE = "Welcome";
    private static final String MENU_TITLE = "Menu";
    protected static enum WELCOME_PAGE_IMG { PASSION_TEA_CO, LEAF, ORGANIC, TEA_CUP, HERBAL_TEA, LOOSE_TEA, FLAVORED_TEA };
    protected static enum MENU_LINKS { MENU, MORE_1, MORE_2, HERBAL_TEA, LOOSE_TEA, FLAVORED_TEA, SEE_COLLECTION1, SEE_COLLECTION2, SEE_COLLECTION3 };

    // constructor
    public PassionTeaCoWelcomePO() throws Exception {
        super();

        setTitle(WELCOME_TITLE);
    }

    // elements
    @FindBy(css = "img[src*='7cbbd331e278a100b443a12aa4cce77b']")
    protected M teaCupImg;

    @FindBy(xpath = "//h1[contains(text(),'We're passionate about tea')]")
    protected M caption;

    @FindBy(xpath = "//span[contains(text(),'For more than 25 years')]")
    protected M paragraph;

    @FindBy(css = "a[href='http://www.seleniumframework.com']")
    protected M seleniumFramework;

    @FindBy(xpath = "//span[.='Herbal Tea']")
    protected M herbalTea;

    @FindBy(xpath = "//span[.='Loose Tea']")
    protected M looseTea;

    @FindBy(xpath = "//span[.='Flavored Tea']")
    protected M flavoredTea;

    @FindBy(css = "img[src*='d892360c0e73575efa3e5307c619db41']")
    protected M herbalTeaImg;

    @FindBy(css = "img[src*='18f9b21e513a597e4b8d4c805321bbe3']")
    protected M looseTeaImg;

    @FindBy(css = "img[src*='d0554952ea0bea9e79bf01ab564bf666']")
    protected M flavoredTeaImg;

    @FindBy(xpath = "(//span[contains(@class,'button-content')])[1]")
    protected M flavoredTeaCollect;

    @FindBy(xpath = "(//span[contains(@class,'button-content')])[2]")
    protected M herbalTeaCollect;

    @FindBy(xpath = "(//span[contains(@class,'button-content')])[3]")
    protected M looseTeaCollect;

    // abstract methods

    /**
     * setTitle method to set page title
     *
     * @param pageTitle
     */
    @Override
    protected void setTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * getTitle method to get page title
     *
     * @return String
     */
    @Override
    public String getTitle() {
        return this.pageTitle;
    }

    // common methods

    /**
     * verifyImgSrc method to verify page image source
     *
     * @param img
     * @param src
     * @throws AssertionError
     */
    public void verifyImgSrc(WELCOME_PAGE_IMG img, String src) throws AssertionError {
        String getText = null;

        switch(img) {
            case PASSION_TEA_CO:
                getText = passionTeaCoImg.getAttribute("src");
                break;
            case LEAF:
                getText = leafImg.getAttribute("src");
                break;
            case ORGANIC:
                getText = organicImg.getAttribute("src");
                break;
            case TEA_CUP:
                getText = teaCupImg.getAttribute("src");
                break;
            case HERBAL_TEA:
                getText = herbalTeaImg.getAttribute("src");
                break;
            case LOOSE_TEA:
                getText = looseTeaImg.getAttribute("src");
                break;
            case FLAVORED_TEA:
                getText = flavoredTeaImg.getAttribute("src");
                break;
        }

        assertEquals(getText, src, "Verify Image Source");
    }

    /**
     * navigateMenuLink method to navigate page menu links
     *
     * @param link
     * @param title
     * @throws AssertionError
     */
    public void navigateMenuLink(MENU_LINKS link, String title) throws Exception {
        String index = null;
        WebDriver driver = CreateDriver.getInstance().getDriver();

        switch(link) {
            case HERBAL_TEA:
                index = "1";
                break;
            case MENU:
                index = "2";
                break;
            case SEE_COLLECTION3:
                index = "3";
                break;
            case MORE_2:
                index = "4";
                break;
            case MORE_1:
                index = "5";
                break;
            case LOOSE_TEA:
                index = "6";
                break;
            case SEE_COLLECTION1:
                index = "7";
                break;
            case SEE_COLLECTION2:
                index = "8";
                break;
            case FLAVORED_TEA:
                index = "9";
                break;
        }

        // Firefox occasionally fails to execute WebDriver API click
        String query = "(//a[@href='menu.html'])" + "[" + index + "]";

        try {
            driver.findElement(By.xpath(query)).click();
            BrowserUtils.waitFor(MENU_TITLE, Global_VARS.TIMEOUT_ELEMENT);
        }

        // make 2nd attempt with JavaScript API click
        catch(TimeoutException e) {
            BrowserUtils.click(By.xpath(query));
            BrowserUtils.waitFor(MENU_TITLE, Global_VARS.TIMEOUT_ELEMENT);
        }

        assertEquals(MENU_TITLE, title, "Navigate Menu Link");
    }

}
