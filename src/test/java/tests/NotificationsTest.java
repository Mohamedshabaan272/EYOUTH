package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class NotificationsTest {

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

        // فتح صفحة الإشعارات
        driver.get("https://eyouthlearning.com/notifications"); // عدل الرابط لو فيه صفحة إشعارات فعلية
    }

    @Test(priority = 1)
    public void verifyNewLessonNotification() {
        By notifItem = By.xpath("//div[contains(@class,'notification-item') and contains(text(),'new lesson')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(notifItem));
        Assert.assertTrue(driver.findElement(notifItem).isDisplayed(),
                "Notification for new lesson/module should be displayed");
    }

    @Test(priority = 2)
    public void verifyScheduleChangeNotification() {
        By notifItem = By.xpath("//div[contains(@class,'notification-item') and contains(text(),'schedule change')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(notifItem));
        Assert.assertTrue(driver.findElement(notifItem).isDisplayed(),
                "Notification for schedule change should be displayed");
    }

    @Test(priority = 3)
    public void verifyReminderNotification() {
        By notifItem = By.xpath("//div[contains(@class,'notification-item') and contains(text(),'reminder')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(notifItem));
        Assert.assertTrue(driver.findElement(notifItem).isDisplayed(),
                "Reminder notification should be displayed");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
