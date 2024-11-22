package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Leave {
    CommonActions action;

    WebDriver leavedriver;
    //locators
    By leaveList =By.xpath("//h5[@class='oxd-text oxd-text--h5 oxd-table-filter-title']");
    //By assignLeave = By.xpath("//a[@class='oxd-topbar-body-nav-tab-item']");
    By assignLeave = By.xpath("/html/body/div/div[1]/div[1]/header/div[2]/nav/ul/li[7]");

    //By employeeName = By.xpath("//div[@class='oxd-autocomplete-text-input oxd-autocomplete-text-input--active']");
    By employeeName = By.xpath("//input[@placeholder='Type for hints...']");

    By autocompleteName = By.xpath("//div[@class='oxd-autocomplete-dropdown --positon-bottom']");
    By fromDate = By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[1]/div/div[2]/div/div/input");
    //By date = By.xpath("//input[@placeholder='yyyy-dd-mm']");
    public By toDate = By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div[2]/div/div[2]/div/div/input");

    By leaveTypeIcon = By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']");
    //By leave = By.xpath("//div[class='oxd-select-text-input']");
    By leave = By.xpath("//div[class='oxd-select-dropdown --positon-bottom']");
    //By leave = By.xpath("//div[role='listbox']");
    By leaveTypeSelect = By.xpath("//div[@class='oxd-select-text-input' and text()='CAN - Personal']");
    //By fromDate = By.xpath("//i[@class='oxd-icon bi-calendar oxd-date-input-icon']" );
    By assign = By.xpath("//button [@type='submit']");

    private final String baseXPath = "/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[3]/div/div";
    // Define locators using the base xpath
    private List<By> dateFields = Arrays.asList(
            By.xpath(baseXPath + "[1]/div/div[2]/div/div/input"), // fromDate
            By.xpath(baseXPath + "[2]/div/div[2]/div/div/input")  // toDate
    );

    private static final Logger logger = LoggerFactory.getLogger(Leave.class);
    //constru0ctor
    public Leave(WebDriver driver) {
        leavedriver=driver;
    }
    //actions
    public boolean isLeaveListDisplayed() {
        WebDriverWait wait = new WebDriverWait(leavedriver, Duration.ofSeconds(10));
        WebElement leavelistVis = wait.until(ExpectedConditions.visibilityOfElementLocated(leaveList));
        return leavedriver.findElement(leaveList).isDisplayed();
    }
    public void clickAssign()
    {
        WebElement assignleaveLink = leavedriver.findElement(assignLeave);
        assignleaveLink.click();
    }




    public void typeEmployeeName(String employeename) {
        action = new CommonActions(leavedriver);
        action.WaitForElement(employeeName);

        leavedriver.findElement(employeeName).sendKeys(employeename);

        // Wait briefly to allow the autocomplete options to appear
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        WebDriverWait wait = new WebDriverWait(leavedriver, Duration.ofSeconds(15));

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'oxd-autocomplete-dropdown')]//div[@role='option']")));

        logger.info("Number of options found: " + options.size());

        for (WebElement option : options) {
            logger.info("Option found: " + option.getText());
            if (option.getText().equals(employeename)) {
                logger.info("Selecting employee name: " + option.getText());

                wait.until(ExpectedConditions.elementToBeClickable(option));
                ((JavascriptExecutor) leavedriver).executeScript("arguments[0].scrollIntoView(true);", option);

                try {
                    option.click();
                    logger.info("Clicked on: " + employeename);
                } catch (Exception e) {
                    logger.error("Standard click failed. Trying JavaScript click.", e);
                    ((JavascriptExecutor) leavedriver).executeScript("arguments[0].click();", option);
                }
                break; // Exit after selecting the option
            }
        }
    }


    public void leaveType(String leaveTypeName) {
        action = new CommonActions(leavedriver);

        // Wait for the leave type icon to be clickable and click it
        WebDriverWait wait = new WebDriverWait(leavedriver, Duration.ofSeconds(10));
        WebElement LeaveTypeLink = wait.until(ExpectedConditions.elementToBeClickable(leaveTypeIcon));
        LeaveTypeLink.click();

        // Short wait to allow dropdown options to become visible
        try {
            Thread.sleep(500); // Adjust time as necessary
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait for the dropdown options to be present and visible
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'oxd-select-dropdown')]//div[@role='option']")));

        // Iterate through the list to find the desired leave type
        for (WebElement option : options) {
            logger.info("Option found: " + option.getText());
            if (option.getText().equals(leaveTypeName)) {
                logger.info("Selecting leave type: " + option.getText());
                ((JavascriptExecutor) leavedriver).executeScript("arguments[0].scrollIntoView(true);", option);
                option.click(); // Attempt standard click
                break;
            }
        }
    }



    public void LeaveFromDate(String fromdate) {
        action = new CommonActions(leavedriver);
        action.WaitForElement(fromDate);

        JavascriptExecutor js = (JavascriptExecutor) leavedriver;
        js.executeScript("arguments[0].value = arguments[1];", leavedriver.findElement(fromDate), fromdate);

        // Wait for a moment to ensure the value is set
        try {
            Thread.sleep(500); // Adjust the duration as necessary
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void LeaveToDate(String todate) {
        action = new CommonActions(leavedriver);
        action.WaitForElement(toDate);

        JavascriptExecutor js = (JavascriptExecutor) leavedriver;
        js.executeScript("arguments[0].value = arguments[1];", leavedriver.findElement(toDate), todate);

        // Wait for a moment to ensure the value is set
        try {
            Thread.sleep(500); // Adjust the duration as necessary
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }





    public void ClickAssignBtn() {
        action = new CommonActions(leavedriver);
        action.WaitForElement(assign);

        // Verify the dates before clicking with explicit wait
        WebDriverWait wait = new WebDriverWait(leavedriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeToBe(leavedriver.findElement(fromDate), "value", "2024-19-10"));
        wait.until(ExpectedConditions.attributeToBe(leavedriver.findElement(toDate), "value", "2024-15-12"));

        String fromDateValue = leavedriver.findElement(fromDate).getAttribute("value");
        String toDateValue = leavedriver.findElement(toDate).getAttribute("value");
        System.out.println("From Date: " + fromDateValue);
        System.out.println("To Date: " + toDateValue);

        leavedriver.findElement(assign).click();
    }



    public void leaveDates(String fromDateInput, String toDateInput) {
        action = new CommonActions(leavedriver);

        // Define the XPath for both date fields
        By dateInputXPath = By.xpath("//input[@placeholder='yyyy-dd-mm']");

        // Wait until the elements are present
        WebDriverWait wait = new WebDriverWait(leavedriver, Duration.ofSeconds(10)); // Use Duration
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dateInputXPath));

        // Find all elements that match the XPath
        List<WebElement> dateElements = leavedriver.findElements(dateInputXPath);

        // Check if we found the expected number of elements
        if (dateElements.size() >= 2) {
            // Set the from date
            WebElement fromDateElement = dateElements.get(0);
            fromDateElement.clear();
            fromDateElement.sendKeys(fromDateInput);

            // Set the to date
            WebElement toDateElement = dateElements.get(1);
            toDateElement.clear();
            toDateElement.sendKeys(toDateInput);
        } else {
            System.out.println("Error: Expected two date input fields, found: " + dateElements.size());
        }
    }



}
