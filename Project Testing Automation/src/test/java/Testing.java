import Pages.Dashboard;
import Pages.Login;
import Pages.Performance;
import Pages.Leave;
import net.bytebuddy.build.Plugin;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class Testing {

    WebDriver driver;
    //WebDriverWait wait;
    Login loginPage;
    Dashboard dashboardPage;
    Performance performancePage;
    Leave leavePage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //wait = new WebDriverWait(driver, 10); // Using WebDriverWait
        driver.get("https://opensource-demo.orangehrmlive.com");
        loginPage = new Login(driver);
    }


    // Positive login test case
    @Test(priority = 1, testName = "Login Test Case")
    public void login() {
        loginPage.typeUsername("Admin");
        loginPage.typePassword("admin123");
        dashboardPage = loginPage.clickLogin();

        // Assert that login was successful, for example by checking the presence of a dashboard element
        Assert.assertTrue(dashboardPage.isUserLoggedIn(), "User should be logged in.");
    }


    // Dashboard checks following successful login
    @Test(priority = 2, testName = "Dashboard Test Case", dependsOnMethods = "login")
    public void dashboardCheck() {
        Assert.assertTrue(dashboardPage.isNavigationDisplayed(), "Navigation should be displayed.");
        Assert.assertTrue(dashboardPage.areItemsLabeled(), "Items should be labeled.");
        dashboardPage.redirectCheck();

        // Instantiating the Performance object after the dashboard check
        performancePage = new Performance(driver);
    }


    // Performance tab checks
    @Test(priority = 3, testName = "Performance Test Case", dependsOnMethods = "dashboardCheck")
    public void performanceCheck() {
        Assert.assertTrue(performancePage.isEmployeeReviewDisplayed(), "Employees Review dropdown should be displayed.");
        //Assert.assertTrue(dashboardPage.areItemsLabeled(), "Items should be labeled.");
        //dashboardPage.redirectCheck();

        // Instantiating the Performance object after the dashboard check
        //performancePage = new Performance(driver);
    }


    //Leave tab checks
    @Test(priority = 4, testName = "Leave List Visibility",dependsOnMethods = "dashboardCheck")
        public void leavelistCheck(){
            dashboardPage.clickLeave();
            leavePage = new Leave(driver);
            Assert.assertTrue(leavePage.isLeaveListDisplayed(),"Leave List should be displayed.");
    }


    //Assigning leave to employee checks
    @Test(priority = 5, testName = "FillingLeave")
    public void assignLeave() {
        leavePage.clickAssign();
        leavePage = new Leave(driver);
        leavePage.typeEmployeeName("Joseph Evans");
        leavePage.leaveType("CAN - Personal");

        leavePage.LeaveFromDate("2024-19-10");

        // Use an explicit wait for the to date field to be clickable
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.elementToBeClickable(leavePage.toDate));

        leavePage.LeaveToDate("2024-15-12");
       // leavePage.leaveDates("2024-10-01", "2024-10-10");
        //leavePage.ClickAssignBtn();
    }

     //Logout test case
    @Test(priority = 6, testName = "logout", dependsOnMethods = "dashboardCheck")
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement profiledropdwn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='oxd-userdropdown-tab']")));
        profiledropdwn.click();

        //driver.findElement(By.xpath("//a[@class='/oxd-userdropdown-tab']")).click();
        WebElement logout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/web/index.php/auth/logout']")));
        logout.click();

        //driver.findElement(By.xpath("//a[@href='/web/index.php/auth/logout']")).click();
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login", "URL after logout");

    }

    //login with invalid user test case
    @Test(priority = 7, testName = "Negative Login Test Case", dependsOnMethods = "logout")
    public void negativeLogin() {
        // Attempt to login with invalid credentials
        loginPage.typeUsername("InvalidUser");
        loginPage.typePassword("InvalidPassword");
        dashboardPage = loginPage.clickLogin();

        // Assert that the login was unsuccessful by checking for an error message
        // Assuming there is an error message element you can check for
        By errorMessage = By.xpath("//div[@class='oxd-alert-content oxd-alert-content--error']"); // Adjust the locator as needed
        Assert.assertTrue(driver.findElements(errorMessage).isEmpty(), "invalid credentials");
    }



//   @AfterClass
//   public void tearDown() {
//       if (driver != null) {
//            driver.quit(); // Clean up the WebDriver instance
//        }
//    }
}
