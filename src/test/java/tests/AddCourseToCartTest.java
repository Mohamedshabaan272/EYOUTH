package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class AddCourseToCartTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // تسجيل الدخول
        driver.get("https://eyouthlearning.com/login");
        driver.findElement(By.name("email")).sendKeys("testuser123@example.com");
        driver.findElement(By.name("password")).sendKeys("Pass@1234");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/home"));
    }

    @Test(priority = 1)
    public void addCourseFromListingPage() {
        driver.get("https://eyouthlearning.com/all-courses");

        By firstAddBtn = By.cssSelector(".course-card:first-child .add-to-cart-btn");
        wait.until(ExpectedConditions.elementToBeClickable(firstAddBtn)).click();

        By cartCount = By.cssSelector(".cart-count");
        String countText = wait.until(ExpectedConditions.visibilityOfElementLocated(cartCount)).getText();
        Assert.assertEquals(countText, "1", "Cart count should be 1 after adding a course");
    }

    @Test(priority = 2)
    public void preventDuplicateCourse() {
        driver.get("https://eyouthlearning.com/all-courses");

        By firstAddBtn = By.cssSelector(".course-card:first-child .add-to-cart-btn");
        wait.until(ExpectedConditions.elementToBeClickable(firstAddBtn)).click();

        By cartCount = By.cssSelector(".cart-count");
        String countText = driver.findElement(cartCount).getText();
        Assert.assertEquals(countText, "1", "Cart count should not increase when adding duplicate course");
    }

    @Test(priority = 3)
    public void removeCourseFromCart() {
        driver.get("https://eyouthlearning.com/cart");

        List<WebElement> removeBtns = driver.findElements(By.cssSelector(".remove-course-btn"));
        int initialCount = removeBtns.size();

        if (initialCount > 0) {
            removeBtns.get(0).click();
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(".remove-course-btn"), initialCount));
            int newCount = driver.findElements(By.cssSelector(".remove-course-btn")).size();
            Assert.assertTrue(newCount < initialCount, "Course should be removed from cart");
        }
    }

    @Test(priority = 4)
    public void cartPersistsAfterLogout() {
        // أضف كورس
        driver.get("https://eyouthlearning.com/all-courses");
        By firstAddBtn = By.cssSelector(".course-card:first-child .add-to-cart-btn");
        wait.until(ExpectedConditions.elementToBeClickable(firstAddBtn)).click();

        // سجل خروج
        driver.get("https://eyouthlearning.com/logout");

        // سجل دخول
        driver.get("https://eyouthlearning.com/login");
        driver.findElement(By.name("email")).sendKeys("testuser123@example.com");
        driver.findElement(By.name("password")).sendKeys("Pass@1234");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/home"));

        // تحقق من السلة
        driver.get("https://eyouthlearning.com/cart");
        Assert.assertTrue(driver.findElements(By.cssSelector(".course-card")).size() > 0,
                "Cart should persist after logout and login");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
