package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.*;

public class CrossBrowserDeviceTest {

    WebDriver driver;

    @Parameters({"browser", "width", "height"})
    @BeforeTest
    public void setUp(String browser, int width, int height) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().window().setSize(new Dimension(width, height));
        driver.get("https://eyouthlearning.com");
    }

    @Test
    public void verifyWebsiteLoads() {
        String title = driver.getTitle();
        Assert.assertTrue(title != null && !title.isEmpty(),
                "Website title should not be empty, page loaded successfully");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
