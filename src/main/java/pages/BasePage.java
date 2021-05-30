package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObject.PageObject;
import pages.containers.Cart;

public class BasePage extends PageObject {

    private Cart cart;

    @FindBy(xpath = "//div[contains(@class,'header-bottom__cart')]")
    private WebElement openCartButton;

    @FindBy(xpath = "//div[@class='header-bottom search_mobile_display']//div[@class='active-cart-item js_cart_count']")
    private WebElement cartItemCount;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public Cart clickCart(){
        openCartButton.click();

        return getCartContent();
    }

    public String getCartItemCountText(){
        return cartItemCount.getText();
    }

    private Cart getCartContent(){
        cart = new Cart(getDriver());
        return cart;
    }
}
