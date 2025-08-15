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

public class CourseBrowsingTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void searchCourseByName() {
        driver.get("https://eyouthlearning.com/all-courses");

        By searchBox = By.cssSelector("input[placeholder*='Search'], input[type='search']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        driver.findElement(searchBox).sendKeys("Business");

        By searchBtn = By.cssSelector("button[type='submit'], .search-btn");
        driver.findElement(searchBtn).click();

        By courseTitle = By.cssSelector(".course-title");
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseTitle));

        Assert.assertTrue(driver.findElement(courseTitle).getText().toLowerCase().contains("business"),
                "Search results should match the course name");
    }

    @Test(priority = 2)
    public void applyCourseFilters() {
        driver.get("https://eyouthlearning.com/all-courses");

        By categoryFilter = By.cssSelector("select[name='category']");
        driver.findElement(categoryFilter).click();
        driver.findElement(By.xpath("//option[contains(text(),'Business')]")).click();

        By priceFilter = By.cssSelector("select[name='price']");
        driver.findElement(priceFilter).click();
        driver.findElement(By.xpath("//option[contains(text(),'Free')]")).click();

        By deliveryFilter = By.cssSelector("select[name='delivery']");
        driver.findElement(deliveryFilter).click();
        driver.findElement(By.xpath("//option[contains(text(),'Online')]")).click();

        By applyBtn = By.cssSelector("button.apply-filters");
        driver.findElement(applyBtn).click();

        By courseCard = By.cssSelector(".course-card");
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseCard));
        Assert.assertTrue(driver.findElements(courseCard).size() > 0, "Filtered results should be displayed");
    }

    @Test(priority = 3)
    public void navigateToCourseDetails() {
        driver.get("https://eyouthlearning.com/all-courses");

        By courseCard = By.cssSelector(".course-card");
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseCard));

        WebElement firstCourse = driver.findElements(courseCard).get(0);
        String courseName = firstCourse.getText();
        firstCourse.click();

        wait.until(ExpectedConditions.urlContains("/course/"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/course/"),
                "Should navigate to course details page for " + courseName);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

