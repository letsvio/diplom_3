package pages;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

    protected String userEmail;
    protected String userPassword;
    protected String userName;

    @BeforeEach
    @Step("Подготовка тестового пользователя и открытие браузера")
    void setUp() {

        // Генерация данных
        userEmail = FakerData.email();
        userPassword = FakerData.password();
        userName = FakerData.name();
        UserClient.createUser(userEmail, userPassword, userName);
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

        if (userEmail != null) {
            String token = UserClient.loginAndGetToken(userEmail, userPassword);
            if (token != null) {
                UserClient.deleteUser("Bearer " + token);
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
