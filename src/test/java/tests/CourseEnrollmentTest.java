package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CourseEnrollmentTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://eyouthlearning.com/login");
        driver.findElement(By.name("email")).sendKeys("testuser123@example.com");
        driver.findElement(By.name("password")).sendKeys("Pass@1234");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/home"));
    }

    @Test(priority = 1)
    public void enrollInFreeCourse() {
        driver.get("https://eyouthlearning.com/course/free-course-slug"); // عدل الرابط حسب الكورس

        By enrollBtn = By.cssSelector("button.enroll-btn");
        wait.until(ExpectedConditions.elementToBeClickable(enrollBtn)).click();

        By successMsg = By.cssSelector(".success-message, .text-success");
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg));
        Assert.assertTrue(driver.findElement(successMsg).isDisplayed(),
                "Enrollment success message should be visible");
    }

    @Test(priority = 2)
    public void enrollInPaidCourse() {
        driver.get("https://eyouthlearning.com/course/paid-course-slug");

        By enrollBtn = By.cssSelector("button.enroll-btn");
        wait.until(ExpectedConditions.elementToBeClickable(enrollBtn)).click();

        wait.until(ExpectedConditions.urlContains("payment"));
        Assert.assertTrue(driver.getCurrentUrl().contains("payment"),
                "User should be redirected to payment gateway for paid course");
    }

    @Test(priority = 3)
    public void verifyCourseInDashboard() {
        driver.get("https://eyouthlearning.com/dashboard");

        By courseCard = By.cssSelector(".course-card");
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseCard));
        Assert.assertTrue(driver.findElements(courseCard).size() > 0,
                "Enrolled courses should be visible in dashboard");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
