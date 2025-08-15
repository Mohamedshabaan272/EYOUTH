package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class AppDownloadButtonTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://eyouthlearning.com"); // عدل إذا كان الرابط مختلف
    }

    @Test(priority = 1)
    public void verifyButtonVisibility() {
        By downloadBtn = By.cssSelector(".app-download-btn");
        wait.until(ExpectedConditions.visibilityOfElementLocated(downloadBtn));
        Assert.assertTrue(driver.findElement(downloadBtn).isDisplayed(),
                "App download button should be visible");
    }

    @Test(priority = 2)
    public void verifyRedirectToGooglePlay() {
        By downloadBtn = By.cssSelector(".app-download-btn");
        String originalWindow = driver.getWindowHandle();
        driver.findElement(downloadBtn).click();

        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("play.google.com"),
                "Should redirect to Google Play link");

        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test(priority = 3)
    public void verifyHoverEffect() {
        By downloadBtn = By.cssSelector(".app-download-btn");
        String beforeHoverColor = driver.findElement(downloadBtn).getCssValue("background-color");

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(downloadBtn)).perform();

        String afterHoverColor = driver.findElement(downloadBtn).getCssValue("background-color");
        Assert.assertNotEquals(beforeHoverColor, afterHoverColor,
                "Button background color should change on hover");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
