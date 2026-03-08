package pages;

import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.FakerData;


import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты регистрации")
public class RegistrationTests extends BaseTest {


    @Test
    @DisplayName("Успешная регистрация")
    void successfulRegistration() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        testEmail = FakerData.email();
        testPassword = FakerData.password();
        testName = FakerData.name();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterName(testName);
        registerPage.enterEmail(testEmail);
        registerPage.enterPassword(testPassword);
        registerPage.clickRegisterButton();

        boolean redirectedCorrectly = wait.until(d -> {
            String url = d.getCurrentUrl();
            return url.contains("/") || url.contains("/login");
        });

        assertTrue(redirectedCorrectly, "Нет редиректа после регистрации. URL: " + driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Ошибка при коротком пароле")
    @Description("Пароль короче 6 символов → отображается сообщение об ошибке")
    void shortPasswordError() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        testEmail = FakerData.email();
        testName = FakerData.name();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();  // ← ОБЯЗАТЕЛЬНО! Переход на страницу регистрации

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterName(testName);  // используй testName из @BeforeEach
        registerPage.enterEmail(testEmail);
        registerPage.enterPassword("12345");
        registerPage.clickRandomLocationOnPage();

        String errorText = registerPage.getPasswordErrorText();

        assertTrue(
                errorText.toLowerCase().contains("некорректный") ||
                        errorText.toLowerCase().contains("пароль"),
                "Не появилась ожидаемая ошибка. Полученный текст: " + errorText
        );
    }
}
