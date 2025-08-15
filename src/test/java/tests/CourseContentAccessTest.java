package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CourseContentAccessTest {

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
    public void accessVideoLecture() {
        By videoItem = By.cssSelector(".course-content .video-item:first-child");
        wait.until(ExpectedConditions.elementToBeClickable(videoItem)).click();

        By videoPlayer = By.cssSelector("video");
        wait.until(ExpectedConditions.visibilityOfElementLocated(videoPlayer));
        Assert.assertTrue(driver.findElement(videoPlayer).isDisplayed(),
                "Video lecture should be visible and playable");
    }

    @Test(priority = 2)
    public void accessQuiz() {
        By quizItem = By.cssSelector(".course-content .quiz-item:first-child");
        wait.until(ExpectedConditions.elementToBeClickable(quizItem)).click();

        By submitBtn = By.cssSelector(".submit-quiz-btn");
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();

        By resultMsg = By.cssSelector(".quiz-result, .success-message");
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultMsg));
        Assert.assertTrue(driver.findElement(resultMsg).isDisplayed(),
                "Quiz result or success message should be displayed");
    }

    @Test(priority = 3)
    public void trackProgress() {
        By progressBar = By.cssSelector(".progress-bar");
        String progressBefore = driver.findElement(progressBar).getAttribute("style");

        // أكمل عنصر محتوى
        By firstContent = By.cssSelector(".course-content .content-item:first-child");
        wait.until(ExpectedConditions.elementToBeClickable(firstContent)).click();

        String progressAfter = driver.findElement(progressBar).getAttribute("style");
        Assert.assertNotEquals(progressBefore, progressAfter, "Progress should update after completing content");
    }

    @Test(priority = 4)
    public void verifyCertificateAfterCompletion() {
        By certificateBadge = By.cssSelector(".completion-badge, .certificate-link");
        wait.until(ExpectedConditions.visibilityOfElementLocated(certificateBadge));
        Assert.assertTrue(driver.findElement(certificateBadge).isDisplayed(),
                "Completion badge or certificate should be visible");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
