package pages.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String BASE_URL_FOR_API = "https://stellarburgers.education-services.ru/api";

    @Step("Создать пользователя через API")
    public static Response createUser(String email, String password, String name) {

        String body = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                email, password, name
        );

        return given()
                .baseUri(BASE_URL_FOR_API)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/auth/register");
    }

    @Step("Логин пользователя и получение accessToken")
    public static String loginAndGetToken(String email, String password) {

        String body = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                email, password
        );

        Response response = given()
                .baseUri(BASE_URL_FOR_API)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/auth/login");

        if (response.statusCode() == 200) {
            return response.jsonPath().getString("accessToken");
        }

        return null;
    }

    @Step("Удаление пользователя по токену")
    public static boolean deleteUser(String accessToken) {

        if (accessToken == null || accessToken.isEmpty()) return false;

        Response response = given()
                .baseUri(BASE_URL_FOR_API)
                .header("Authorization", accessToken)
                .when()
                .delete("/auth/user");

        return response.statusCode() == 202;
    }
}
