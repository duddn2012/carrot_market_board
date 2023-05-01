package com.boardPractice.demo.repository;

import com.boardPractice.demo.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(int postId);
    List<Post> findAll();
}
