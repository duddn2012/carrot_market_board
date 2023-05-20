package com.boardPractice.demo.repository;

import com.boardPractice.demo.domain.Post;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaPostRepository implements PostRepository{

    private final EntityManager em;

    public JpaPostRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public Optional<Post> findById(int postId) {
        Post post = em.find(Post.class, postId);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("select m from Post m", Post.class).getResultList();
    }

}
