package pages;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.FakerData;


import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты регистрации")
public class RegistrationTests extends BaseTest {


    private String testEmail;
    private String testPassword;
    private String testName;

    @BeforeEach
    @Override
    @Step("Создание нового пользователя перед каждым тестом авторизации")
    void setUp() {
        super.setUp(); // браузер + ожидание главной
        testEmail = FakerData.email();
        testPassword = FakerData.password();
        testName = FakerData.name();
    }

    @Test
    @DisplayName("Успешная регистрация")
    void successfulRegistration() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();  // ← это было пропущено

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
