package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class Login {

    WebDriver loginDriver;
    CommonActions action;
    //locators
    By userName = By.xpath("//input[@placeholder='Username']");

    By password = By.xpath("//input[@name='password']");

    By loginButton = By.xpath("//button[@type='submit']");

    //constructor
    public Login(WebDriver driver){

        loginDriver = driver;
    }

    //actions
    public void typeUsername(String username){
        action = new CommonActions(loginDriver);
        action.WaitForElement(userName);

        //WebDriverWait wait = new WebDriverWait(loginDriver, Duration.ofSeconds(10));
        //WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(userName));
        loginDriver.findElement(userName).sendKeys(username);
    }

    public void typePassword(String password){
        loginDriver.findElement(this.password).sendKeys(password);

    }

    public Dashboard clickLogin(){
        loginDriver.findElement(loginButton).click();
        return new Dashboard(loginDriver);
    }
}

