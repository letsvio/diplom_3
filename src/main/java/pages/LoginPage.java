package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private final By emailField = By.name("name"); // или By.xpath("//label[contains(text(),'Email')]/following::input")
    private final By passwordField = By.name("Пароль");
    private final By loginButton = By.xpath("//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
    }

    @Step("Ввести пароль: {password}")
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    @Step("Нажать кнопку «Войти» (надёжный способ: Actions + JS-клик)")
    public void clickLoginButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));

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

    @Step("Перейти на страницу регистрации")
    public void clickRegisterLink() {
        By locator = By.xpath("//a[text()='Зарегистрироваться']");
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(locator));
        link.click();
    }

    @Step("Выполнить полный вход: email + пароль + клик")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
}