package pages;

import static com.codeborne.selenide.Selenide.open;

public class CommonPage extends AbstractPage {

    public void openUrl(String url) {
        System.out.println(">>> Открытие страницы по адресу URL: " + url);
        open(url);
    }
}
