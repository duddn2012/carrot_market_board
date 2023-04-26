package com.boardPractice.demo.repository;

import com.boardPractice.demo.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(int user_id);
    List<User> findAll();
}
