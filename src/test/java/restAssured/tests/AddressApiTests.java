package restAssured.tests;

import restAssured.config.RestAssuredConfig;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Тесты DaData API: Адреса")
class AddressApiTests {

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("GET /detectAddressByIp - определение города по IP")
    void testDetectAddressByIp() {
        RequestSpecification spec = RestAssuredConfig.getDetectSpec();

        given()
                .spec(spec)
                .queryParam("ip", "77.88.21.11")
                .when()
                .get("/detectAddressByIp")
                .then()
                .statusCode(200)
                .body("location", notNullValue())
                .body("location.value", not(emptyOrNullString()))
                .body("location.data.city", equalTo("Москва"))
                .body("location.data.country", equalTo("Россия"))
                .body("location.data.geo_lat", notNullValue())
                .log().all();
    }

    @Test
    @DisplayName("POST /suggest/address - подсказки по запросу 'москва хабар'")
    void testSuggestAddress() {
        RequestSpecification spec = RestAssuredConfig.getSuggestSpec();

        String requestBody = """
            {
                "query": "москва хабар",
                "count": 5
            }
            """;

        given()
                .spec(spec)
                .body(requestBody)
                .when()
                .post("/suggest/address")
                .then()
                .statusCode(200)
                .body("suggestions", notNullValue())
                .body("suggestions.size()", greaterThan(0))
                .body("suggestions[0].value", containsStringIgnoringCase("Москва"))
                .log().all();
    }

    @Test
    @DisplayName("POST /suggest/address - валидация параметров запроса")
    void testSuggestAddressWithOptionalParams() {
        RequestSpecification spec = RestAssuredConfig.getSuggestSpec();

        String requestBody = """
            {
                "query": "самара авроры 7",
                "count": 3,
                "language": "ru"
            }
            """;

        given()
                .spec(spec)
                .body(requestBody)
                .when()
                .post("/suggest/address")
                .then()
                .statusCode(200)
                .body("suggestions", hasSize(lessThanOrEqualTo(3)))
                .body("suggestions[0].data.postal_code", notNullValue())
                .log().all();
    }

    @Test
    @DisplayName("GET /detectAddressByIp - обработка невалидного IP")
    void testDetectAddressByIpInvalid() {
        RequestSpecification spec = RestAssuredConfig.getDetectSpec();

        given()
                .spec(spec)
                .queryParam("ip", "invalid.ip.address")
                .when()
                .get("/detectAddressByIp")
                .then()
                .statusCode(400)
                .log().all();
    }

    @Test
    @DisplayName("POST /suggest/address - обработка 403 при неверном токене")
    void testSuggestAddressWrongToken() {
        String wrongToken = "invalid_token_12345";

        given()
                .baseUri("https://suggestions.dadata.ru/suggestions/api/4_1/rs")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Token " + wrongToken)
                .body("{\"query\": \"тест\"}")
                .when()
                .post("/suggest/address")
                .then()
                .statusCode(403)
                .log().all();
    }
}