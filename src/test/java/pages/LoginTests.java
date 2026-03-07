package pages;

import pages.api.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.FakerData;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты авторизации")
public class LoginTests extends BaseTest {

    // Локальные переменные для пользователя в каждом тесте
    private String testEmail;
    private String testPassword;
    private String testName;

    @BeforeEach
    @Override
    @Step("Создание нового пользователя перед каждым тестом авторизации")
    void setUp() {
        super.setUp(); // вызов родительского метода (браузер + базовое ожидание)

        // Генерируем нового пользователя для каждого теста
        testEmail = FakerData.email();
        testPassword = FakerData.password();
        testName = FakerData.name();

        // Регистрируем через API (быстрее и надёжнее, чем UI)
        UserClient.createUser(testEmail, testPassword, testName);
    }

    @Test
    @DisplayName("Вход через кнопку «Войти в аккаунт»")
    @Description("Переход → ввод данных → профиль открыт")
    void loginFromMainPageButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(testEmail, testPassword);

        assertTrue(new ProfilePage(driver).isProfilePageLoadedButtonPostOrder(),
                "Профиль не открылся после входа через кнопку «Войти в аккаунт»");
    }

    @Test
    @DisplayName("Вход через «Личный кабинет»")
    @Description("Клик по «Личный кабинет» → ввод данных → профиль открыт")
    void loginFromProfileButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(testEmail, testPassword);

        assertTrue(new ProfilePage(driver).isProfilePageLoadedButtonPostOrder(),
                "Профиль не открылся после входа через «Личный кабинет»");
    }

    @Test
    @DisplayName("Вход через страницу регистрации")
    @Description("Главная → вход → регистрация → вход → профиль открыт")
    void loginFromRegistrationPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginLink();
        loginPage.login(testEmail, testPassword);
        assertTrue(new ProfilePage(driver).isProfilePageLoadedButtonPostOrder(),
                "Профиль не открылся после входа через страницу регистрации");
    }

}