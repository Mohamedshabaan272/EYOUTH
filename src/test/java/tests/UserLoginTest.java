package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UserLoginTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void loginWithValidCredentials() {
        driver.get("https://eyouthlearning.com/login");

        driver.findElement(By.name("email")).sendKeys("moo123@example.com");
        driver.findElement(By.name("password")).sendKeys("moo@1234");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/home"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/home"),
                "User should be redirected to home page after login");
    }

    @Test(priority = 2)
    public void loginWithInvalidCredentials() {
        driver.get("https://eyouthlearning.com/login");

        driver.findElement(By.name("email")).sendKeys("wrong@example.com");
        driver.findElement(By.name("password")).sendKeys("WrongPass");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        By errorMsg = By.cssSelector(".error-message, .text-danger");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));

        Assert.assertTrue(driver.findElement(errorMsg).isDisplayed(),
                "Error message should be visible for invalid credentials");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
