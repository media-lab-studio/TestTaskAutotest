package restAssured.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RestAssuredConfig {

    private static final String API_TOKEN = System.getenv("DADATA_TOKEN")
            != null ? System.getenv("DADATA_TOKEN")
            : "Тута мой токен, но я не покажу";

    public static RequestSpecification getSuggestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://suggestions.dadata.ru/suggestions/api/4_1/rs")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Token " + API_TOKEN)
                .build();
    }

    public static RequestSpecification getDetectSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://dadata.ru/api/v2")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Token " + API_TOKEN)
                .build();
    }
}