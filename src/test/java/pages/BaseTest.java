package pages;


import pages.api.UserClient;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.FakerData;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected String testEmail;
    protected String testPassword;
    protected String testName;

    @BeforeEach
    @Step("Подготовка тестового пользователя и открытие браузера")
    void setUp() {

        testEmail = FakerData.email();
        testPassword = FakerData.password();
        testName = FakerData.name();
        UserClient.createUser(testEmail, testPassword, testName);
        String browserName = System.getProperty("browser", "chrome");

        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        driver = new Browser().getWebDriver(browserName, isHeadless);

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(BasePage.BASE_URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()='Войти в аккаунт']")
        ));
    }


    @AfterEach
    @Step("Удаление пользователя и закрытие браузера")
    void tearDown() {
        if (testEmail != null) {
            String token = UserClient.loginAndGetToken(testEmail, testPassword);
            if (token != null && !token.isEmpty()) {
                UserClient.deleteUser("Bearer " + token);
            }
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
