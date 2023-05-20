package com.boardPractice.demo.repository;

import com.boardPractice.demo.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository{
    private static Map<Integer, User> store = new HashMap<>();
    private static int sequence = 0;

    @Override
    public User save(User user) {
        user.setUserId(++sequence);
        store.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(int user_id) {
        return Optional.ofNullable(store.get(user_id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(int userId) {
        store.remove(userId);
    }

    @Override
    public Optional<User> updateById(int userId, User user) {
        User updatedUser = store.replace(userId, user);
        return Optional.of(updatedUser);
    }
}
