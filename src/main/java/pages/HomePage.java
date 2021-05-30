package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//span[@class='sidebar-item']")
    private WebElement sideBar;

    @FindBy(xpath = "//div[@class='menu-lvl first-level']")
    private WebElement sideBarOuterCategories;

    private WebElement sideBarInnerCategories;

    private WebElement sideBarProducts;

    @FindBy(xpath = "//div[@class='category-items flex-wrap']//a[@class='category-box']")
    private List<WebElement> popularItems;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickSideBar() {
        sideBar.click();

        return this;
    }

    public HomePage goToInnerCategory(String name) {
        sideBarInnerCategories = getSubCategory(
                selectCategory(sideBarOuterCategories, name));

        waitForElementToLoad(sideBarInnerCategories, 10);

        return this;
    }

    public void clickInnerCategoryItem(String name) {
        selectCategory(sideBarInnerCategories, name).click();
    }

    public HomePage goToProducts(String name) {
        sideBarProducts = getSubCategory(
                selectCategory(sideBarInnerCategories, name));

        waitForElementToLoad(sideBarProducts, 10);

        return this;
    }

    //TODO return page for sub-category name doesn't match (notebooks - xiaomi)
    public void clickProductWithFilter(String name) {
        selectCategory(sideBarProducts, name).click();

        //return ProductFactory.getProduct(getDriver(), name);
    }

    public WebElement selectCategory(WebElement source, String name) {
        WebElement element = source.findElement(By.xpath(".//a[contains(@href,'" + name + "')]"));

        getActions().moveToElement(element)
                .build().perform();

        return element;
    }

    public WebElement getSubCategory(WebElement element) {
        return element.findElement(
                By.xpath(".//following-sibling::div[contains(@class,'menu-lvl')]"));
    }

    public void clickPopularItem(int n) {
        popularItems.get(n).click();
    }
}
