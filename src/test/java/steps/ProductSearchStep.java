package steps;

import helpers.Config;
import helpers.WebDriverContainer;
import org.junit.jupiter.api.Test;

public class ProductSearchStep extends AbstractStepDefinitions {

    private final Config config = Config.getInstance();

    @Test
    void openOtcURL() {
        String url = config.getConfigProperty("OtcURL");
        commonPage.openUrl(url);
        WebDriverContainer.getInstance().getWebDriver();
    }

    @Test
    void searchProduct() {
        searchProductPage
                .waitProductSearchBar(config.getConfigProperty("PlaceHolderText"))
                .setTextProductSearchBar(config.getConfigProperty("ProductNameText"))
                .clickProductSearchButton();
    }
}
