package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//div[@class='prod-cart__descr']")
    private List<WebElement> prodElementsDesc;

    @FindBy(xpath = "//div[@class='filter-block']//div[@class='filter-wrapp']")
    private List<WebElement> filterBlocks;

    @FindBy(xpath = "//div[@class='prod-cart__img']")
    private List<WebElement> productItems;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getProdElementsDescText() {
        return prodElementsDesc.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public ProductPage clickFilterBlocks(List<String> values) {

        for (String name : values) {
            By xpath = By.xpath(".//input[@value='" + name + "']");

            waitForPageToLoad(10);

            WebElement checkBox = filterBlocks.stream()
                    .filter(element ->
                            !element.findElements(xpath)
                                    .isEmpty())
                    .findFirst()
                    .orElseThrow();

            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", checkBox.findElement(xpath));
        }

        return this;
    }

    public ProductPage openClosedFilterBox(String name) {
        filterBlocks.stream()
                .map(element -> element.findElement(By.tagName("p")))
                .filter(elementText -> {
                    if (elementText.getAttribute("class").contains("close-filter")
                            && elementText.getText().contains(name)) {

                        elementText.click();

                        return true;
                    }

                    return false;
                })
                .findFirst()
                .orElseThrow();

        return this;
    }

    public ProductPage clickProductIconByNumber(int number) {
        productItems.get(number).click();

        return this;
    }

}
