package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.SignupPage;

import static org.junit.jupiter.api.Assertions.*;


public class SignupTest {
    private WebDriver driver;
    private SignupPage signupPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        signupPage = new SignupPage(driver);
        signupPage.open();
    }
    @Test
    void successfulSignupShowsConfirmationMessage() {
        String testEmail = "test.automation@example.com";

        signupPage.subscribe(testEmail);

        assertTrue(signupPage.isSuccessMessageDisplayed(), "Success message should be visible after subscribing");
        assertEquals("Thanks for subscribing!", signupPage.getSuccessHeadingText());
        assertEquals(testEmail, signupPage.getSuccessEmailText());
    }

    @ParameterizedTest(name = "email \"{0}\" is rejected with \"{1}\"")
    @CsvSource({
            "'', Whoops! It looks like this is empty",
            "not-an-email, Valid email required"
    })
    void invalidEmailShowsValidationError(String email, String expectedError) {
        signupPage.enterEmail(email);
        signupPage.clickSubscribe();

        assertEquals(expectedError, signupPage.getEmailErrorText());
        assertFalse(signupPage.isSuccessMessageDisplayed(), "Success message should not appear for invalid input");
    }
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
