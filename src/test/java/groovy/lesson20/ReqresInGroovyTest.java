package groovy.lesson20;

import groovy.lesson20.models.UnsuccessfulModel;
import org.junit.jupiter.api.*;

import static groovy.lesson20.spec.Specification.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ReqresInGroovyTest {

    @Test
    @DisplayName("Проверка на код 404, когда ресурс не найден")
    public void resourceNotFoundUsingGroovyTest() {
        step("Вызов GET с несуществующей страницей", () -> {
            given(requestSpec)
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
    void unsuccessfulRegisterUsingGroovyTest() {
        step("Ввод email в поле для регистрации для неуспешной регистрации", () -> {
            UnsuccessfulModel unsuccessfulModel = new UnsuccessfulModel();
            unsuccessfulModel.setEmail("sydney@fife");

            given(requestSpec)
                    .body(unsuccessfulModel)
                    .when()
                    .post("/register")
                    .then()
                    .spec(responseSpec400)
                    .body("error", is("Missing password"));

        });
    }

    @Test
    @DisplayName("Проверка успешного удаления пользователя с кодом 204")
    void deletingUserUsingGroovyTest() {

        given(requestSpec)
                .when()
                .delete("/api/users/2")
                .then()
                .log().status()
                .spec(responseSpec204);
    }

    @Test
    @DisplayName("Проверка создания нового пользователя")
    void createUserUsingGroovyTest() {
        String testDataCreateUser = "{\"name\": \"lina\", \"job\": \"lina\"}";

        step("Отправка метода POST на создание пользования с body", () -> {

            given(requestSpec)
                    .when()
                    .body(testDataCreateUser)
                    .post("/users")
                    .then()
                    .log().status()
                    .log().body()
                    .spec(responseSpec201)
                    .body("name", is("lina"))
                    .body("job", is("lina"));
        });
    }

    @Test
    @DisplayName("Проверка, что имя первого пользователя в списке пользователей - George")
    void checkFirstNameInListUserPageUsingGroovyTest() {

        step("Вызов эндпоинта users с query-параметром 1", () -> {
            given(requestSpec)
                    .when()
                    .get("/users?page=1")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body("data[0].first_name", is("George"));
        });
    }
}
