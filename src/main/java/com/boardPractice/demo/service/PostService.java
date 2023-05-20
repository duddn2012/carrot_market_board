package com.boardPractice.demo.service;

import com.boardPractice.demo.domain.Post;
import com.boardPractice.demo.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public int posting(Post post){
        validateDuplicatePost(post);
        postRepository.save(post);
        return post.getPostId();
    }

    private void validateDuplicatePost(Post post) {
        postRepository.findById(post.getUserId())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 포스팅입니다.");
                });
    }

    public List<Post> findPosts(){return postRepository.findAll();}

    public Optional<Post> findOne(int postId){return postRepository.findById(postId);}
}
