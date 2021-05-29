package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObject.PageObject;
import pageObject.ProductPage;
import pages.products.ProductFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class HomePage extends PageObject {

    @FindBy(xpath = "//span[@class='sidebar-item']")
    private WebElement sideBar;

    @FindBy(xpath = "//div[@class='menu-lvl first-level']")
    private WebElement outerCategories;

    private WebElement innerCategories;

    private WebElement products;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickSideBar(){
        sideBar.click();

        return this;
    }

    public HomePage goToInnerCategory(String name){
        innerCategories = getSubCategory(
                selectCategory(outerCategories, name));

        waitForElementToLoad(innerCategories, 10);

        return this;
    }

   /* public HomePage clickInnerCategoryItem(String name){
        products = selectCategory(innerCategories, name);

        waitForElementToLoad(products, 10);

        products.click();

        return this;
    }*/

    public void clickInnerCategoryItem(String name){
        selectCategory(innerCategories, name).click();
    }

    public HomePage goToProducts(String name){
        products = getSubCategory(
                selectCategory(innerCategories, name));

        waitForElementToLoad(products, 10);

        return this;
    }

    //TODO return page for sub-category name doesn't match (notebooks - xiaomi)
    public void clickProductWithFilter(String name){
        selectCategory(products, name).click();

        //return ProductFactory.getProduct(getDriver(), name);
    }

    public WebElement selectCategory(WebElement source, String name){
        WebElement element = source.findElement(By.xpath(".//a[contains(@href,'"+name+"')]"));

        getActions().moveToElement(element)
                .build().perform();

        return element;
    }

    public WebElement getSubCategory(WebElement element){
        return element.findElement(
                By.xpath(".//following-sibling::div[contains(@class,'menu-lvl')]"));
    }
}
