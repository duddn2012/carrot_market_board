package com.boardPractice.demo.repository;

import com.boardPractice.demo.domain.User;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaUserRepository implements UserRepository{

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(int userId) {
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }

    @Override
    public void deleteById(int userId) {
        User user = em.find(User.class, userId);
        if(user != null){
            em.remove(user);
        }
    }

    @Override
    public Optional<User> updateById(int userId, User updatedUser) {
        User existedUser = em.find(User.class, userId);

        if(updatedUser != null){
            existedUser.setNickname(updatedUser.getNickname());
            existedUser.setBadge(updatedUser.getBadge());
            existedUser.setEmail(updatedUser.getEmail());
            existedUser.setLocation(updatedUser.getLocation());
            existedUser.setManner(updatedUser.getManner());
            existedUser.setPhone(updatedUser.getPhone());

            em.persist(existedUser);
            return Optional.of(existedUser);
        }
        return Optional.empty();
    }
}
