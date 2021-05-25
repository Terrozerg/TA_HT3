import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

public class AvicTests {

    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Actions actions;

    @BeforeTest
    public void sysVariableSetUp() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");

        actions = new Actions(driver);
        webDriverWait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testGoToNotebookSectionFromSidebar() {
        WebElement sideBar = driver.findElement(By.xpath("//span[@class='sidebar-item']"));
        sideBar.click();

        WebElement slider = driver.findElement(By.className("category-slider"));

        WebElement notebooks = slider.findElement(By.xpath(".//a[contains(@href,'noutbuki-i-aksessuaryi')]"));
        actions.moveToElement(notebooks)
                .build().perform();

        WebElement innerList = notebooks.findElement(By.xpath(".//parent::li//div[contains(@class,'second-level')]"));
        actions.moveToElement(innerList.findElement(By.className("sidebar-item")))
                .build().perform();

        WebElement xiaomi = innerList.findElement(By.xpath(".//a[contains(@href,'xiaomi')]"));

        webDriverWait.until(ExpectedConditions.visibilityOf(xiaomi));

        actions.moveToElement(xiaomi)
                .click().build().perform();


        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='prod-cart__descr']"));
        for (WebElement webElement : elementList) {
            Assert.assertTrue(webElement.getText().contains("Xiaomi"));
        }
    }

    @Test
    public void testFilterBlock() {
        WebElement sideBar = driver.findElement(By.xpath("//span[@class='sidebar-item']"));
        sideBar.click();

        WebElement slider = driver.findElement(By.className("category-slider"));

        WebElement notebooks = slider.findElement(By.xpath(".//a[contains(@href,'noutbuki-i-aksessuaryi')]"));
        actions.moveToElement(notebooks)
                .build().perform();

        WebElement innerList = notebooks.findElement(By.xpath(".//parent::li//div[contains(@class,'second-level')]"));

        WebElement item = innerList.findElement(By.xpath(".//a[@href='https://avic.ua/noutbuki']"));

        webDriverWait.until(ExpectedConditions.visibilityOf(item));

        actions.moveToElement(item)
                .click().build().perform();

        List<WebElement> filterBlocks;
        List<String> filterValues = List.of("dlya-biznesa", "tonkij-i-lyogkij");

        for (String value : filterValues) {
            webDriverWait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));

            filterBlocks = driver.findElements(By.xpath("//div[@class='filter-block']//div[@class='filter-wrapp']"));

            WebElement checkBox = filterBlocks.stream()
                    .map(element -> {
                        actions.moveToElement(element).build().perform();

                        return element.findElement(By.xpath(".//input[@value='" + value + "']"));
                    })
                    .findFirst()
                    .orElseThrow();

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", checkBox);

        }

        Assert.assertTrue(driver.getCurrentUrl().contains("tip-noutbuka--dlya-biznesa--tonkij-i-lyogkij"));

    }

    @Test
    public void testFilterBlockHiddenElementsAndVerifyElementProperty(){
        WebElement sideBar = driver.findElement(By.xpath("//span[@class='sidebar-item']"));
        sideBar.click();

        WebElement slider = driver.findElement(By.className("category-slider"));

        WebElement notebooks = slider.findElement(By.xpath(".//a[contains(@href,'noutbuki-i-aksessuaryi')]"));
        actions.moveToElement(notebooks)
                .build().perform();

        WebElement innerList = notebooks.findElement(By.xpath(".//parent::li//div[contains(@class,'second-level')]"));

        WebElement item = innerList.findElement(By.xpath(".//a[@href='https://avic.ua/noutbuki']"));

        webDriverWait.until(ExpectedConditions.visibilityOf(item));

        actions.moveToElement(item)
                .click().build().perform();

        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='filter-block']//div[@class='filter-wrapp']"));
        for (WebElement element : elements) {
            WebElement clickable = element.findElement(By.tagName("p"));

            if(clickable.getAttribute("class").contains("close-filter")){
                if(clickable.getText().contains("Сенсорный экран")){
                    actions.moveToElement(clickable).click().build().perform();

                    webDriverWait.until(webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));

                    WebElement innerCheckBox = element.findElement(By.xpath(".//input[@value='est']"));

                    actions.moveToElement(innerCheckBox).click().build().perform();

                    break;
                }
            }
        }

        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='prod-cart__img']"));
        elementList.get(2).click();

        List<WebElement> characteristics = driver.findElements(
                By.xpath("//section[@class='prod-feature-all']//td[@class='field']"));

        Optional<String> result = characteristics.stream()
                .map(WebElement::getText)
                .filter(text->text.contains("сенсорный"))
                .findFirst();

        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void testAddAndRemoveFromCart(){
        String expected = "0";

        List<WebElement> categoryItems = driver.findElements(
                By.xpath("//div[@class='category-items flex-wrap']//a[@class='category-box']"));
        categoryItems.get(0).click();
        WebElement buyButton = driver.findElement(
                By.xpath("//section[@class='product-info']//a[contains(@class,'main-btn--large')]"));

        buyButton.click();

        WebElement cart = driver.findElement(By.id("js_cart"));

        webDriverWait.until(ExpectedConditions.visibilityOf(cart));

        cart.findElement(By.xpath(".//i[contains(@class,'js-btn-close')]"))
                .click();

        webDriverWait.until(ExpectedConditions.invisibilityOf(cart));

        WebElement headerCart = driver.findElement(
                By.xpath("//div[@class='header-bottom search_mobile_display']//div[@class='active-cart-item js_cart_count']"));

        Assert.assertEquals(headerCart.getText(), expected);
    }

    @AfterMethod
    public void shutDown() {
        driver.close();
    }
}
