package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {


    public WebDriver getWebDriver(String browserName, boolean headless) {
        if ("chrome".equalsIgnoreCase(browserName)) {
            ChromeOptions options = new ChromeOptions();

            if (headless) {
                options.addArguments("--headless=new");           // современный headless (Chrome 109+)
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");  // важно для корректных скриншотов и layout
                options.addArguments("--disable-gpu");            // иногда требуется в headless
            }

            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-extensions");
            options.addArguments("--start-maximized"); // полезно даже в headless

            return new ChromeDriver(options);
        }

        if ("yandex".equalsIgnoreCase(browserName)) {
            System.setProperty("webdriver.chrome.driver", "C:\\tools\\yandexdriver.exe");

            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Users\\violence\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");

            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-gpu");
            }

            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");

            return new ChromeDriver(options);
        }

        throw new IllegalArgumentException("Неизвестный браузер: " + browserName);
    }


    public WebDriver getWebDriver(String browserName) {
        return getWebDriver(browserName, true);
    }
}