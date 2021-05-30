package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

public class ItemPage extends BasePage {

    @FindBy(xpath = "//section[@class='prod-feature-all']//td[@class='field']")
    private List<WebElement> characteristics;

    @FindBy(xpath = "//section[@class='product-info']//a[contains(@class,'main-btn--large')]")
    private WebElement itemBuyButton;

    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public Optional<String> getCharacteristicPresent(String name) {
        return characteristics.stream()
                .map(WebElement::getText)
                .filter(text -> text.contains(name))
                .findFirst();
    }

    public ItemPage clickBuyButton() {
        itemBuyButton.click();

        return this;
    }
}
