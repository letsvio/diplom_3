package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class MainPage extends BasePage {

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By profileButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By constructorButton = By.xpath("//p[text()='Конструктор']");
    private final By logo = By.cssSelector("a[href='/'");

    private final By bunsTab = By.xpath("//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']/parent::div");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Нажать кнопку «Войти в аккаунт» на главной странице")
    public void clickLoginButton() {
        waitForClickable(loginButton);
        driver.findElement(loginButton).click();
    }

    @Step("Нажать кнопку «Личный Кабинет» в шапке")
    public void clickProfileButton() {
        waitForClickable(profileButton);
        driver.findElement(profileButton).click();
    }

    @Step("Нажать кнопку «Конструктор» в шапке")
    public void clickConstructorButton() {
        waitForClickable(constructorButton);
        driver.findElement(constructorButton).click();
    }

    @Step("Нажать на логотип Stellar Burgers")
    public void clickLogo() {
        waitForClickable(logo);
        driver.findElement(logo).click();
    }

    @Step("Перейти во вкладку «Булки»")
    public void clickBunsTab() {
        reliableClick(bunsTab);
    }

    @Step("Перейти во вкладку «Соусы»")
    public void clickSaucesTab() {
        reliableClick(saucesTab);
    }

    @Step("Перейти во вкладку «Начинки»")
    public void clickFillingsTab() {
        reliableClick(fillingsTab);
    }

    @Step("Проверить, что вкладка «Булки» активна")
    public boolean isBunsTabActive() {
        return isTabActive(bunsTab);
    }

    @Step("Проверить, что вкладка «Соусы» активна")
    public boolean isSaucesTabActive() {
        return isTabActive(saucesTab);
    }

    @Step("Проверить, что вкладка «Начинки» активна")
    public boolean isFillingsTabActive() {
        return isTabActive(fillingsTab);
    }

    @Step("Проверить, что вкладка активна по локатору: {locator}")
    private boolean isTabActive(By locator) {
        try {
            waitForVisibility(locator);
            String classes = driver.findElement(locator).getAttribute("class");
            return classes != null && classes.contains("current");
        } catch (Exception e) {
            return false;
        }
    }

    public By getBunsTabLocator() {
        return bunsTab;
    }

    public By getSaucesTabLocator() {
        return saucesTab;
    }

    public By getFillingsTabLocator() {
        return fillingsTab;
    }

    private void reliableClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));


        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", element
        );

        wait.until(ExpectedConditions.elementToBeClickable(locator));

        try {
            element.click();
            return;
        } catch (Exception ignored) {}

        try {
            new Actions(driver)
                    .moveToElement(element)
                    .pause(Duration.ofMillis(200))
                    .click()
                    .perform();
            return;
        } catch (Exception ignored) {}

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

}
