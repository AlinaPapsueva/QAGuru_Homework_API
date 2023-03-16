package restassured.lesson18;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInTests {
    String BASE_URL = "https://reqres.in";

    @Test
    @DisplayName("Проверка на код 404, когда ресурс не найден")
    void resourceNotFoundTest() {

        given()
                .log().uri()
                .when()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .get(Endpoints.getSingleResourceN + 23)
                .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    @DisplayName("Проверка на неуспешную регистрацию")
    void checkUnsuccessfulRegisterTest() {
        String testData = "{\"email\":\"sydney@fife\"}";

        given()
                .log().uri()
                .when()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .body(testData)
                .post(Endpoints.postRegister)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Проверка успешного удаления пользователя с кодом 204")
    void deletingUserTest() {

        given()
                .log().uri()
                .when()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .delete(Endpoints.deleteDeleteN + 2)
                .then()
                .log().status()
                .statusCode(204);
    }

    @Test
    @DisplayName("Проверка создания нового пользователя")
    void createUserTest() {
        String testDataCreateUser = "{\"name\": \"lina\", \"job\": \"lina\"}";

        given()
                .log().uri()
                .when()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .body(testDataCreateUser)
                .post(Endpoints.postCreate)
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("lina"))
                .body("job", is("lina"));
    }

    @Test
    @DisplayName("Проверка, что имя первого пользователя в списке пользователей - George")
    void checkFirstNameInListUserPage() {
        given()
                .log().uri()
                .when()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .get(Endpoints.getListUsersP + 1)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data[0].first_name", is("George"));
    }
}