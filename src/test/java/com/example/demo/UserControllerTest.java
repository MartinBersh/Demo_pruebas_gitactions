package com.example.demo;

import com.example.demo.entitie.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @LocalServerPort
    private int port;
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("John Doe"));
    }

    @Test
    void testGetUser() {
        User createdUser = new User();
        createdUser.setName("Jane Doe");
        createdUser.setEmail("jane.doe@example.com");

        Integer userId = given()
                .contentType(ContentType.JSON)
                .body(createdUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .when()
                .get("/users/{id}", userId)
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("name", equalTo("Jane Doe"))
                .body("email", equalTo("jane.doe@example.com"));
    }

    @Test
    void testUpdateUser() {
        User createdUser = new User();
        createdUser.setName("Old Name");
        createdUser.setEmail("old.email@example.com");

        Integer userId = given()
                .contentType(ContentType.JSON)
                .body(createdUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().path("id");

        createdUser.setName("Updated Name");
        createdUser.setEmail("updated.email@example.com");

        given()
                .contentType(ContentType.JSON)
                .body(createdUser)
                .when()
                .put("/users/{id}", userId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"))
                .body("email", equalTo("updated.email@example.com"));
    }

    @Test
    void testDeleteUser() {
        User createdUser = new User();
        createdUser.setName("Delete User");
        createdUser.setEmail("delete.user@example.com");

        Integer userId = given()
                .contentType(ContentType.JSON)
                .body(createdUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .when()
                .delete("/users/{id}", userId)
                .then()
                .statusCode(200);
    }

    @Test
    void testTransferUser() {
        User user1 = new User();
        user1.setName("User 4");
        user1.setEmail("user4@example.com");

        User user2 = new User();
        user2.setName("User 2");
        user2.setEmail("user2@example.com");

        Integer user1Id = given()
                .contentType(ContentType.JSON)
                .body(user1)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().path("id");

        Integer user2Id = given()
                .contentType(ContentType.JSON)
                .body(user2)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .when()
                .param("fromId", user1Id)
                .param("toId", user2Id)
                .patch("/users/transfer")
                .then()
                .statusCode(200)
                .body(equalTo("Transferencia completada"));
    }
}
