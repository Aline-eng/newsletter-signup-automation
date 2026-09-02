package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupTest {
    private static final String URL = "https://aline-eng.github.io/NewsLetter-Signup/";
    private WebDriver driver;
    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
    }
    @Test
    void successfulSignupShowsConfirmationMessage() {
        String testEmail = "test.automation@example.com";
        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys(testEmail);

        WebElement subscribeButton = driver.findElement(By.cssSelector("#signup-form button[type='submit']"));
        subscribeButton.click();

        WebElement successHeading = driver.findElement(By.id("success-heading"));
        assertTrue(successHeading.isDisplayed(), "Success message should be visible after subscribing");
        assertEquals("Thanks for subscribing!", successHeading.getText());

        WebElement successEmail = driver.findElement(By.id("success-email"));
        assertEquals(testEmail, successEmail.getText());
    }
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
