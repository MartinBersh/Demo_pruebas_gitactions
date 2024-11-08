package com.example.demo;

import com.example.demo.entitie.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserControllerTest {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:8080";  // Ajusta la URL de tu servidor
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(201)  // Verifica que el estado sea 201 (CREATED)
                .body("name", equalTo("John Doe"));
    }

    @Test
    void testGetUser() {
        // Crear un usuario previamente para probar
        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setName("Jane Doe");
        createdUser.setEmail("jane.doe@example.com");

        // Crear el usuario
        Integer userId = given()
                .contentType(ContentType.JSON)
                .body(createdUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().path("id");  // Extraemos el ID del usuario creado

        // Obtener el usuario por ID
        given()
                .when()
                .get("/users/{id}", userId)
                .then()
                .statusCode(200)  // Verifica que el estado sea 200 (OK)
                .body("id", equalTo(userId))
                .body("name", equalTo("Jane Doe"))
                .body("email", equalTo("jane.doe@example.com"));
    }

    @Test
    void testUpdateUser() {
        // Crear un usuario previamente para actualizar
        User createdUser = new User();
        createdUser.setId(2L);
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

        // Actualizar el usuario
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

  /*  @Test
    void testDeleteUser() {
        // Crear un usuario previamente para eliminar
        User createdUser = new User();
        createdUser.setId(3L);
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

        // Eliminar el usuario
        given()
                .when()
                .delete("/users/{id}", userId)
                .then()
                .statusCode(200);

        // Intentar obtener el usuario eliminado
        given()
                .when()
                .get("/users/{id}", userId)
                .then()
                .statusCode(404);  // El usuario ya no debe existir
    }

    @Test
    void testTransferUser() {
        // Crear dos usuarios
        User user1 = new User();
        user1.setId(3L);
        user1.setName("User 4");
        user1.setEmail("user4@example.com");

        User user2 = new User();
        user1.setId(4L);
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

        // Realizar la transferencia
        given()
                .when()
                .param("fromId", user1Id)
                .param("toId", user2Id)
                .patch("/users/transfer")
                .then()
                .statusCode(200)
                .body(equalTo("Transfer successful"));
    }*/
}
