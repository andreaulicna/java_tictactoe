package com.app.tictactoe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.tictactoe.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
