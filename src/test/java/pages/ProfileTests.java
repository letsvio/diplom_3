package pages;

import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты личного кабинета")
public class ProfileTests extends BaseTest {

    private void login() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(userEmail, userPassword);
    }

    @Test
    @DisplayName("Переход в профиль")
    @Description("Авторизованный пользователь открывает профиль")
    void goToProfile() {
        login();

       ProfilePage profilePage = new ProfilePage(driver);

        profilePage.waitForProfileButtonLoadedAndClick();
        assertTrue(new ProfilePage(driver).isProfilePageLoadedProfileString());
    }

    @Test
    @DisplayName("Переход в конструктор из профиля")
    void goToConstructorFromProfile() {
        login();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickConstructorButton();

        assertTrue(driver.getCurrentUrl().contains("/"));
    }

    @Test
    @DisplayName("Переход на главную через логотип")
    void goToMainViaLogo() {
        login();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogo();

        assertTrue(driver.getCurrentUrl().contains("/"));
    }

    @Test
    @DisplayName("Выход из аккаунта")
    void logout() {
        login();

        ProfilePage profilePage = new ProfilePage(driver);

        profilePage.clickLoginButtonProfile();
        profilePage.clickLogout();
        // 4. Ждём редиректа и появления индикатора разлогинивания
        boolean isLoggedOut = wait.until(d -> {
            String url = d.getCurrentUrl();
            if (url.contains("/login")) {
                return d.findElements(By.xpath("//a[contains(text(),'Зарегистрироваться')]"))
                        .stream().anyMatch(WebElement::isDisplayed);
            }
            return false;

        });
        assertTrue(isLoggedOut,
                        "После выхода из аккаунта пользователь не разлогинен. " +
                                "Текущий URL: " + driver.getCurrentUrl() + ". " +
                                "Ожидалась кнопка 'Войти в аккаунт' или ссылка 'Зарегистрироваться'");

    }
}
