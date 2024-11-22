package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Dashboard {

     WebDriver dashboardDriver;

     //locators
     By sideNavigation = By.xpath("//div[@class='oxd-sidepanel-body']");
     By itemLabel = By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name']");
     By performance = By.xpath("//a[@href='/web/index.php/performance/viewPerformanceModule']");
     //By leave = By.xpath("//span [@class = 'class=\"oxd-text oxd-text--span oxd-main-menu-item--name']");

     By leave = By.xpath("//a[@href='/web/index.php/leave/viewLeaveModule']");

     //constructor
    public Dashboard(WebDriver driver) {

        dashboardDriver = driver;
    }

    //actions
    public boolean isNavigationDisplayed() {
        WebDriverWait wait = new WebDriverWait(dashboardDriver, Duration.ofSeconds(10));
        WebElement sideNav = wait.until(ExpectedConditions.visibilityOfElementLocated(sideNavigation));
        return dashboardDriver.findElement(sideNavigation).isDisplayed();
    }

    public boolean areItemsLabeled() {

        return dashboardDriver.findElement(itemLabel).isDisplayed();
    }

    public void redirectCheck() {
        WebElement performanceLink = dashboardDriver.findElement(performance);
        performanceLink.click();
    }

    public boolean isUserLoggedIn() {
        try {
            // Assuming there's a specific element that only appears when the user is logged in
            By welcomeMessage = By.xpath("//div[@class='oxd-topbar-header-title']");
            WebDriverWait wait = new WebDriverWait(dashboardDriver, Duration.ofSeconds(10));
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage));
            return message.isDisplayed();
        } catch (Exception e) {
            // Handle exception (e.g., log it)
            return false;
        }
    }

    public void clickLeave()
    {
        WebElement leaveLink = dashboardDriver.findElement(leave);
        leaveLink.click();
    }

}