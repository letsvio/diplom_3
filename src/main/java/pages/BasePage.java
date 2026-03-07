package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public static final String BASE_URL = "https://stellarburgers.education-services.ru/";

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть главную страницу Stellar Burgers")
    public void openMainPage() {
        driver.get(BASE_URL);
    }

    @Step("Ожидать видимость элемента: {locator}")
    protected void waitForVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Ожидать кликабельность элемента: {locator}")
    protected void waitForClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
