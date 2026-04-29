package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SearchProductPage extends AbstractPage{

    /*******************************************************************************************************************
     *                                        Локаторы элементов страницы
     ******************************************************************************************************************/
    private final SelenideElement productSearchBar = $("input.SearchInput-module__xN-moW__selectInput")
            .as("Строка поиска товара");
    private final SelenideElement productSearchButton =
            $(".SearchBlockLanding-module__DgRBKa__searchModule button[data-variant='filled'][data-size='xl']")
                    .as("Кнопка [Найти] в строке поиска товара");
    /*******************************************************************************************************************
     *                                          Методы страницы
     ******************************************************************************************************************/

    /**
     * Ожидает строку поиска товара с placeHolder
     * @param placeHolder текст placeholder
     */
    public SearchProductPage waitProductSearchBar(String placeHolder) {
        System.out.println("Ожидает строку поиска товара с placeHolder: " + placeHolder);
        productSearchBar
                .shouldBe(visible, shortDuration)
                .shouldHave(attribute("placeholder",placeHolder), shortDuration);
        return this;
    }

    /**
     * Вводит название товара в строку поиска товара
     * @param text Название товара
     */
    public SearchProductPage setTextProductSearchBar(String text) {
        System.out.println("Вводит название товара в строку поиска товара");
        productSearchBar.shouldBe(visible, shortDuration).setValue(text);
        return this;
    }

    /**
     * Нажимает на кнопку [Найти] в строке поиска товара
     */
    public SearchProductPage clickProductSearchButton() {
        System.out.println("Нажимает на кнопку [Найти] в строке поиска товара");
        productSearchButton.shouldBe(visible, shortDuration).click();
        return this;
    }
}
