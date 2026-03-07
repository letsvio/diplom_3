package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Random;

public class RegisterPage extends BasePage {


    public RegisterPage(WebDriver driver) {
        super(driver);
    }
    // Поле "Имя"
    private final By nameInput = By.xpath("//div[contains(@class, 'input_type_text')]//label[text()='Имя']/following-sibling::input");

    // Поле "Email"
    private final By emailInput = By.xpath("//div[contains(@class, 'input_type_text')]//label[contains(text(),'Email')]/following-sibling::input");

    // Поле "Пароль"
    private final By passwordInput = By.xpath("//div[contains(@class, 'input_type_password')]//label[contains(text(),'Пароль')]/following-sibling::input");

    // Кнопка "Зарегистрироваться"
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");

    // Ссылка "Войти"
    private final By loginLink = By.xpath("//a[text()='Войти']");

    // Текст ошибки под паролем
    private final By passwordError = By.xpath("//p[normalize-space(text())='Некорректный пароль']");

    @Step("Ввести имя")
    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
    }

    @Step("Ввести email")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    @Step("Нажать кнопку «Зарегистрироваться»")
    public void clickRegisterButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    @Step("Кликнуть в случайное место на странице")
    public void clickRandomLocationOnPage() {
        Dimension size = driver.manage().window().getSize();

        // Получаем случайные координаты внутри видимой области (с небольшим отступом от краёв)
        int x = new Random().nextInt(size.getWidth() - 200) + 100;   // от 100 до width-100
        int y = new Random().nextInt(size.getHeight() - 200) + 100;  // от 100 до height-100

        new Actions(driver)
                .moveByOffset(x, y)
                .click()
                .perform();
    }

    @Step("Нажать кнопку «Войти»")
    public void clickLoginLink() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        // Прокрутка + JS-клик (надёжно для React)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    @Step("Получить текст ошибки пароля")
    public String getPasswordErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).getText();
    }
}