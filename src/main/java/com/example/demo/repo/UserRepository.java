package com.example.demo.repo;

import com.example.demo.entitie.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
