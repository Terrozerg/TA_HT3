package pages.containers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObject.PageObject;

import java.util.List;

public class Cart extends PageObject {

    @FindBy(id = "js_cart")
    private WebElement cartPopup;

    @FindBy(xpath = "//i[contains(@class,'js-btn-close')]")
    private List<WebElement> removeItemFromCartButtons;

    public Cart(WebDriver driver) {
        super(driver);
    }

    public void clickRemoveItemFromCartButton(int n) {
        waitForElementToLoad(cartPopup, 10);

        removeItemFromCartButtons.get(n).click();

        waitForElementToUnload(cartPopup, 10);
    }
}
