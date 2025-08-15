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

public class CourseReviewsRatingsTest {

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

        // فتح كورس مسجل فيه المستخدم
        driver.get("https://eyouthlearning.com/course/enrolled-course-slug"); // عدل الرابط للكورس الفعلي
    }

    @Test(priority = 1)
    public void submitStarRating() {
        By fourStar = By.cssSelector(".rating-stars span[data-value='4']");
        wait.until(ExpectedConditions.elementToBeClickable(fourStar)).click();

        By submitBtn = By.cssSelector(".submit-review-btn");
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();

        By successMsg = By.cssSelector(".success-message");
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg));
        Assert.assertTrue(driver.findElement(successMsg).isDisplayed(),
                "Success message should be displayed after rating submission");
    }

    @Test(priority = 2)
    public void submitTextReview() {
        By reviewBox = By.cssSelector("textarea.review-text");
        wait.until(ExpectedConditions.visibilityOfElementLocated(reviewBox)).sendKeys("Great course, very helpful!");

        By submitBtn = By.cssSelector(".submit-review-btn");
        driver.findElement(submitBtn).click();

        By reviewText = By.xpath("//div[@class='review-content' and contains(text(),'Great course')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(reviewText));
        Assert.assertTrue(driver.findElement(reviewText).isDisplayed(),
                "Review text should appear in the reviews list");
    }

    @Test(priority = 3)
    public void viewExistingReviews() {
        By reviewSection = By.cssSelector(".course-reviews");
        wait.until(ExpectedConditions.visibilityOfElementLocated(reviewSection));

        int reviewCount = driver.findElements(By.cssSelector(".review-item")).size();
        Assert.assertTrue(reviewCount > 0, "There should be at least one review visible");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

