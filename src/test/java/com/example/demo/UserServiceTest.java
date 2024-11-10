package com.example.demo;

import com.example.demo.entitie.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ActiveProfiles("test")
public class UserServiceTest {

    private UserService userService;
    private List<User> users; // Lista en memoria para simular la base de datos

    @BeforeEach
    void setUp() {
        // Inicializa la lista en memoria y el servicio
        users = new ArrayList<>();
        userService = new UserService(users); // Pasa la lista al servicio como dependencia
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        userService.createUser(user);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        users.add(user); // Añadir directamente a la lista simulada

        User result = userService.getUserById(1L);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        users.add(user);

        User updatedUser = new User();
        updatedUser.setName("Jane Doe");
        updatedUser.setEmail("jane.doe@example.com");

        User result = userService.updateUser(1L, updatedUser);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane.doe@example.com", result.getEmail());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        users.add(user);

        userService.deleteUser(1L);
        assertEquals(0, users.size());
    }

    @Test
    void testTransferUser() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2");
        user2.setEmail("user2@example.com");

        users.add(user1);
        users.add(user2);

        String result = userService.transferUser(1L, 2L);
        assertEquals("Transferencia completada", result);
        assertEquals("user2@example.com", user1.getEmail());
        assertEquals("user1@example.com", user2.getEmail());
    }
}
