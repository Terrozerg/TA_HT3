import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pages.HomePage;
import pages.products.Notebooks;

public class BaseTest {
    private static final String AVIC_URL = "https://avic.ua/";

    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Actions actions;

    @BeforeTest
    public void profileSetUp(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setDriver(){
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(AVIC_URL);

        actions = new Actions(driver);
        webDriverWait = new WebDriverWait(driver, 10);
    }

    @AfterMethod
    public void shutDown(){
        driver.close();
    }

    public HomePage getHomePage() {
        return new HomePage(driver);
    }

    public Notebooks getNotebooks() {
        return new Notebooks(driver);
    }
}
