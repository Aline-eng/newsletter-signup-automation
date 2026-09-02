package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    private static final String URL = "https://aline-eng.github.io/NewsLetter-Signup/";

    private final WebDriver driver;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "#signup-form button[type='submit']")
    private WebElement subscribeButton;

    @FindBy(id = "email-error")
    private WebElement emailError;

    @FindBy(id = "success-heading")
    private WebElement successHeading;

    @FindBy(id = "success-email")
    private WebElement successEmail;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
    }
    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void clickSubscribe() {
        subscribeButton.click();
    }
    public void subscribe(String email) {
        enterEmail(email);
        clickSubscribe();
    }

    public boolean isSuccessMessageDisplayed() {
        return successHeading.isDisplayed();
    }

    public String getSuccessHeadingText() {
        return successHeading.getText();
    }
    public String getSuccessEmailText() {
        return successEmail.getText();
    }
    public String getEmailErrorText() {
        return emailError.getText();
    }
}
