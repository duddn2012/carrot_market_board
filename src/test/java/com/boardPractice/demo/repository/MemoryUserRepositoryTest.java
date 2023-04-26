package com.boardPractice.demo.repository;

import com.boardPractice.demo.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @Test
    void save() {
        User user = new User();
        user.setUserId(0);

        repository.save(user);

        User result = repository.findById(user.getUserId()).get();
        Assertions.assertEquals(user, result);
        assertThat(user).isEqualTo(result);
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }
}