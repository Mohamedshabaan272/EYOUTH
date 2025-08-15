package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class PasswordResetTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void requestPasswordResetLink() {
        driver.get("https://eyouthlearning.com/forgot-password");

        driver.findElement(By.name("email")).sendKeys("testuser123@example.com");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        By successMsg = By.cssSelector(".success-message, .text-success");
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg));

        Assert.assertTrue(driver.findElement(successMsg).isDisplayed(),
                "Success message should appear after requesting reset link");
    }

    @Test(priority = 2)
    public void resetPasswordWithValidData() {
        // ملاحظة: في الواقع هتحتاج توليد رابط إعادة التعيين من البريد
        // هنا بنفترض إننا فاتحين صفحة تغيير الباسورد من الرابط
        driver.get("https://eyouthlearning.com/reset-password?token=VALID_TOKEN");

        driver.findElement(By.name("password")).sendKeys("NewPass@123");
        driver.findElement(By.name("confirmPassword")).sendKeys("NewPass@123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        By successMsg = By.cssSelector(".success-message, .text-success");
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg));

        Assert.assertTrue(driver.findElement(successMsg).isDisplayed(),
                "Password reset success message should appear");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
