package com.example.demo.service;

import com.example.demo.entitie.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public String transferUser(Long fromUserId, Long toUserId) {
        // Simula una transacción
        User fromUser = userRepository.findById(fromUserId).orElseThrow();
        User toUser = userRepository.findById(toUserId).orElseThrow();
        // Realizar alguna acción, por ejemplo, transferir un campo
        String temp = fromUser.getEmail();
        fromUser.setEmail(toUser.getEmail());
        toUser.setEmail(temp);
        return "Transferencia completada";
    }
}
