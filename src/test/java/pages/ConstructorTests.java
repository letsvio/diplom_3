package pages;

import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты конструктора")
public class ConstructorTests extends BaseTest {

    @Test
    @DisplayName("Переход к разделу «Булки»")
    @Description("Клик по вкладке «Булки» делает её активной")
    void switchToBunsTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickBunsTab();
        assertTrue(mainPage.isBunsTabActive());
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    @Description("Клик по вкладке «Соусы» делает её активной")
    void switchToSaucesTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesTab();
        assertTrue(mainPage.isSaucesTabActive());
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    @Description("Клик по вкладке «Начинки» делает её активной")
    void switchToFillingsTab() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsTab();
        assertTrue(mainPage.isFillingsTabActive());
    }
}
