package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UserRegistrationTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        // شغل المتصفح
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void registerWithValidData() {
        driver.get("https://eyouthlearning.com/signup?redirect=/all-courses");

        // الحقول
        driver.findElement(By.name("name")).sendKeys("Test User");
        driver.findElement(By.name("username")).sendKeys("testuser123");
        driver.findElement(By.name("email")).sendKeys("testuser123@example.com");
        driver.findElement(By.name("password")).sendKeys("Pass@1234");
        driver.findElement(By.name("confirmPassword")).sendKeys("Pass@1234");
        driver.findElement(By.name("phone")).sendKeys("01012345678");

        // القوائم المنسدلة
        driver.findElement(By.name("country")).click();
        driver.findElement(By.xpath("//option[text()='Egypt']")).click();

        driver.findElement(By.name("gender")).click();
        driver.findElement(By.xpath("//option[text()='Male']")).click();

        driver.findElement(By.name("government")).click();
        driver.findElement(By.xpath("//option[text()='Cairo']")).click();

        // زر التسجيل
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // تأكيد الانتقال للـ Home
        wait.until(ExpectedConditions.urlContains("/home"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/home"), "User should be redirected to home page after registration");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
