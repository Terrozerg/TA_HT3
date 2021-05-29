package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class PageObject {
    private WebDriver driver;
    private Actions actions;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
    }

    public WebElement waitForElementToLoad(WebElement element, int timeOut) {
        new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOf(element));

        return element;
    }

    public void waitForPageToLoad(int timeOut) {
        new WebDriverWait(driver, timeOut).until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public Actions getActions() {
        return actions;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
