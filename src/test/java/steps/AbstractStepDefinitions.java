package steps;

import helpers.WebDriverContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import pages.CommonPage;
import pages.SearchProductPage;

public abstract class AbstractStepDefinitions {

    protected static boolean browserInitialized = false;
    protected CommonPage commonPage;
    protected SearchProductPage searchProductPage;


    @BeforeEach
    void ensureBrowserReady() {
        if (!browserInitialized) {
            WebDriverContainer.getInstance().initBrowser();
            browserInitialized = true;
        }
        initPages();
    }

    protected void initPages() {
        commonPage = new CommonPage();
        searchProductPage = new SearchProductPage();
    }

    @AfterAll
    static void closeBrowser() {
        System.out.println(">>> Завершение сеанса");
        WebDriverContainer.getInstance().closeBrowser();
        browserInitialized = false;
    }
}