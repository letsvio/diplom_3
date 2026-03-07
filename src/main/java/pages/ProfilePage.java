package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {

    private final By logoutButton = By.xpath("//button[text()='Выход']");
    private final By buttonPostOrder = By.xpath("//button[text()='Оформить заказ']");
    private final By profileButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By profileString = By.xpath("//a[normalize-space(text())='Профиль']");
    private final By registerButton = By.xpath("//a[text()='Зарегистрироваться']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Ожидать загрузку страницы профиля")
    public void waitForProfilePageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonPostOrder));
    }

    @Step("Нажать кнопку личный кабинет")
    public void waitForProfileButtonLoadedAndClick() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileButton));
        driver.findElement(profileButton).click();
    }

    @Step("Нажать кнопку «Выход» в профиле")
    public void clickLogout() {
        By logoutButton = By.xpath("//button[contains(text(),'Выход')]");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));

        // Прокрутка + JS-клик (самый надёжный способ)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    @Step("Проверить, что главная страница профиля загружена")
    public boolean isProfilePageLoadedButtonPostOrder() {
        try {
            waitForVisibility(buttonPostOrder);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что страница профиля загружена")
    public boolean isProfilePageLoadedProfileString() {
        try {
            waitForVisibility(profileString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что на странице есть кнопка регистрации")
    public Boolean isProfilePageLoadedRegisterButton() {
        try {
            waitForVisibility(registerButton);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Нажать кнопку «Войти» (надёжный способ: Actions + JS-клик)")
    public void clickLoginButtonProfile() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(profileButton));

        // 1. Прокрутка к кнопке в центр видимой области
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});", button);

        // 2. Задержка на завершение скролла/анимации (важно!)
        try {
            Thread.sleep(800);
        } catch (InterruptedException ignored) {}

        // 3. Actions: наводим курсор точно в центр и кликаем
        new Actions(driver)
                .moveToElement(button)
                .pause(300)
                .click()
                .perform();

        // 4. На всякий случай — JS-клик (если Actions не сработает)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }


}
