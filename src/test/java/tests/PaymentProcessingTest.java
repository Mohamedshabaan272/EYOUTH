package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class PaymentProcessingTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // تسجيل دخول وإضافة كورس مدفوع للسلة
        driver.get("https://eyouthlearning.com/login");
        driver.findElement(By.name("email")).sendKeys("testuser123@example.com");
        driver.findElement(By.name("password")).sendKeys("Pass@1234");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/home"));

        driver.get("https://eyouthlearning.com/course/paid-course-slug"); // عدل الرابط
        driver.findElement(By.cssSelector(".add-to-cart-btn")).click();
        driver.get("https://eyouthlearning.com/cart");
        driver.findElement(By.cssSelector(".checkout-btn")).click();
    }

    @Test(priority = 1)
    public void successfulCreditCardPayment() {
        driver.findElement(By.id("payment-creditcard")).click();
        driver.findElement(By.name("cardNumber")).sendKeys("4111111111111111");
        driver.findElement(By.name("expiryDate")).sendKeys("12/30");
        driver.findElement(By.name("cvv")).sendKeys("123");
        driver.findElement(By.cssSelector(".pay-btn")).click();

        By successMsg = By.cssSelector(".success-message, .payment-success");
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg));
        Assert.assertTrue(driver.findElement(successMsg).isDisplayed(),
                "Success message should appear after payment");
    }

    @Test(priority = 2)
    public void failedCreditCardPayment() {
        driver.get("https://eyouthlearning.com/checkout");
        driver.findElement(By.id("payment-creditcard")).click();
        driver.findElement(By.name("cardNumber")).sendKeys("1234567890123456");
        driver.findElement(By.name("expiryDate")).sendKeys("01/20");
        driver.findElement(By.name("cvv")).sendKeys("000");
        driver.findElement(By.cssSelector(".pay-btn")).click();

        By errorMsg = By.cssSelector(".error-message, .payment-error");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));
        Assert.assertTrue(driver.findElement(errorMsg).isDisplayed(),
                "Error message should appear for failed payment");
    }

    @Test(priority = 3)
    public void successfulPayPalPayment() {
        driver.get("https://eyouthlearning.com/checkout");
        driver.findElement(By.id("payment-paypal")).click();

        wait.until(ExpectedConditions.urlContains("paypal.com"));
        Assert.assertTrue(driver.getCurrentUrl().contains("paypal.com"),
                "User should be redirected to PayPal");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
