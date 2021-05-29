package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.products.Notebooks;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ProductPage extends PageObject {

    @FindBy(xpath = "//div[@class='prod-cart__descr']")
    private List<WebElement> prodElementsDesc;

    @FindBy(xpath = "//div[@class='filter-block']//div[@class='filter-wrapp']")
    private List<WebElement> filterBlocks;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getProdElementsDesc(){
        return prodElementsDesc;
    }

    public List<WebElement> getFilterBlocks(){
        return filterBlocks;
    }

    public List<String> getProdElementsDescText() {
        return getProdElementsDesc().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public ProductPage clickFilterBlocks(List<String> values) {
        for (String name : values) {
            waitForPageToLoad(10);

            WebElement checkBox = getFilterBlocks().stream()
                    .map(element -> {
                        getActions().moveToElement(element).build().perform();

                        return element.findElement(By.xpath(".//input[@value='" + name + "']"));
                    })
                    .findFirst()
                    .orElseThrow();

            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", checkBox);
        }

        return this;
    }

    public ProductPage openClosedFilterBox(String name){
        WebElement box = getFilterBlocks().stream()
                .map(element -> element.findElement(By.tagName("p")))
                .filter(element -> element.getAttribute("class").contains("close-filter")
                        && element.getText().contains(name))
                .findFirst()
                .orElseThrow();

        getActions().moveToElement(box)
                .click().build().perform();

        return this;
    }

}
