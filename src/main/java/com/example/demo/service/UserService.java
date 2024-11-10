package com.example.demo.service;

import com.example.demo.entitie.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final List<User> users;
    private final AtomicLong counter = new AtomicLong();

    public UserService(List<User> users) {
        this.users = users;
    }



    public User createUser(User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
        return user;
    }

    public User getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User updateUser(Long id, User user) {
        Optional<User> existingUser = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();

        if (existingUser.isPresent()) {
            User u = existingUser.get();
            u.setName(user.getName());
            u.setEmail(user.getEmail());
            return u;
        }
        return null;
    }

    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    public String transferUser(Long fromUserId, Long toUserId) {
        User fromUser = getUserById(fromUserId);
        User toUser = getUserById(toUserId);

        if (fromUser != null && toUser != null) {
            String temp = fromUser.getEmail();
            fromUser.setEmail(toUser.getEmail());
            toUser.setEmail(temp);
            return "Transferencia completada";
        }
        return "Usuarios no encontrados";
    }
}
