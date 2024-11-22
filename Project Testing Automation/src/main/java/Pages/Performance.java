package Pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Performance {
    WebDriver performanceDriver;

    //locators
    By employeesReview = By.xpath("//div[@class='oxd-table-filter-header']");

    //constructor
    public Performance(WebDriver driver) {

        performanceDriver = driver;
    }

    //actions
    public boolean isEmployeeReviewDisplayed() {
        WebDriverWait wait = new WebDriverWait(performanceDriver, Duration.ofSeconds(10));
        WebElement sideNav = wait.until(ExpectedConditions.visibilityOfElementLocated(employeesReview));
        return performanceDriver.findElement(employeesReview).isDisplayed();
    }
}

