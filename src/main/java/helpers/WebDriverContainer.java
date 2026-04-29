package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class WebDriverContainer {

    private static final String DRIVER_FULL_PATH = "C:/Users/user/IdeaProjects/TestTaskAutotest/src/test/resources/drivers/chromedriver.exe";

    private static volatile WebDriverContainer instance;
    private static boolean initialized = false;

    private WebDriverContainer() {}

    public static WebDriverContainer getInstance() {
        if (instance == null) {
            synchronized (WebDriverContainer.class) {
                if (instance == null) {
                    instance = new WebDriverContainer();
                }
            }
        }
        return instance;
    }

    public synchronized void initBrowser() {
        if (initialized) return;

        // Проверка и установка драйвера
        File driverFile = new File(DRIVER_FULL_PATH);
        if (!driverFile.exists()) {
            throw new IllegalStateException("ChromeDriver не найден: " + DRIVER_FULL_PATH);
        }
        System.setProperty("webdriver.chrome.driver", DRIVER_FULL_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-search-engine-choice-screen");

        //  Конфигурация Selenide
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.browserSize = null;
        Configuration.browserPosition = null;
        Configuration.timeout = 10_000;
        Configuration.pageLoadStrategy = "normal";

        Configuration.browserCapabilities = options;

        initialized = true;
        System.out.println("Браузер инициализирован");
    }

    public void getWebDriver() {
        if (!initialized) initBrowser();
        WebDriverRunner.getWebDriver();
    }

    public void closeBrowser() {
        com.codeborne.selenide.Selenide.closeWebDriver();
        initialized = false;
        System.out.println("Браузер закрыт");
    }
}