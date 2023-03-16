package modelsandspecs.lesson19;

import modelsandspecs.lesson19.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static modelsandspecs.lesson19.spec.Specification.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresInTests {

    @Tag("test_api_with_steps")
    @DisplayName("Проверка на код 404, когда ресурс не найден")
    @Test
    void getListUsers() {
        step("Вызов GET с несуществующей страницей", () -> {
            given()
                    .spec(requestSpec)
                    .when()
                    .get("/api/unknown/23")
                    .then()
                    .spec(responseSpec404);
        });
    }

    @Tag("test_api_with_steps")
    @DisplayName("Проверка на неуспешную регистрацию")
    @Test
    void unsuccessfulRegister() {
        step("Ввод email в поле для регистрации для неуспешной регистрации", () -> {
            UnsuccessfulModel unsuccessfulModel = new UnsuccessfulModel();
            unsuccessfulModel.setEmail("sydney@fife");

            ResponseUnsuccessfulModel unsuccessfulResponse = given()
                    .spec(requestSpec)
                    .body(unsuccessfulModel)
                    .when()
                    .post("/register")
                    .then()
                    .spec(responseSpec400)
                    .extract().as(ResponseUnsuccessfulModel.class);

            assertThat(unsuccessfulResponse.getError()).isEqualTo("Missing password");
        });
    }

    @Tag("test_api_with_steps")
    @DisplayName("Проверка успешного удаления пользователя с кодом 204")
    @Test
    void deleteUsers() {
        step("Удаление пользователя с методом DELETE", () -> {
            given()
                    .spec(requestSpec)
                    .when()
                    .delete("/api/users/2")
                    .then()
                    .spec(responseSpec204);
        });
    }

    @Tag("test_api_with_steps")
    @DisplayName("Проверка создания нового пользователя")
    @Test
    void createUsers() {
        step("Отправка метода POST на создание пользования с body", () -> {
            CreateUserModel createUser = new CreateUserModel();
            createUser.setName("lina");
            createUser.setJob("lina");

            ResponseModel responseModel = given()
                    .spec(requestSpec)
                    .body(createUser)
                    .when()
                    .post("/users").then()
                    .spec(responseSpec201)
                    .extract().as(ResponseModel.class);

            assertThat(responseModel.getName()).isEqualTo("lina");
        });
    }

    @Tag("test_api_with_steps")
    @DisplayName("Проверка работы query-параметров page")
    @Test
    void updateUserInfo() {
        step("Вызов эндпоинта users с query-параметром 1", () -> {

            ResponseModel responseModel = given()
                    .spec(requestSpec)
                    .when()
                    .get("/users?page=1")
                    .then()
                    .spec(responseSpec200)
                    .extract().as(ResponseModel.class);

            assertThat(responseModel.getPage()).isEqualTo("1");
        });
    }
}
