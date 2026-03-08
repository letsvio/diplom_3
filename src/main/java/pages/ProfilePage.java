package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {


    private final By buttonPostOrder = By.xpath("//button[text()='Оформить заказ']");
    private final By profileButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By profileString = By.xpath("//a[normalize-space(text())='Профиль']");


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

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
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


    @Step("Нажать кнопку «Войти»")
    public void clickLoginButtonProfile() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(profileButton));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});", button);

        wait.until(ExpectedConditions.elementToBeClickable(button));

        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }


}
