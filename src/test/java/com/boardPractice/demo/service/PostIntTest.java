package com.boardPractice.demo.service;

import com.boardPractice.demo.domain.Post;
import com.boardPractice.demo.domain.User;
import com.boardPractice.demo.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostIntTest {

    @Autowired PostRepository postRepository;
    @Autowired PostService postService;

    @Test
    @Commit
    void posting() {
        //given
        List<Post> posts = new ArrayList<>();

        for(int i=0;i<10;i++) {
            Post post = new Post();
            post.setCategorycd("0");
            post.setContents("이 물건은 핸드폰입니다."+i);
            post.setHopeaddr("Seoul");
            post.setPostcd("0");
            post.setPrice(100000 * i);
            post.setPriceadvyn("Y");
            post.setShareyn("N");
            post.setTitle("phone sell"+i+"번째 제품");
            post.setUserId(i);
            post.setPoststatuscd("0");

            posts.add(post);
        }

        //when
        List<Integer> saveIds = new ArrayList<>();
        for(int i=0;i<10;i++){
            int saveId = postService.posting(posts.get(i));
            saveIds.add(saveId);
        }

        //then
        for(int i=0;i<10;i++){
            Post findPost = postService.findOne(saveIds.get(i)).get();
            assertThat(posts.get(i).getPostId()).isEqualTo(findPost.getPostId());
        }
    }

    @Test
    void findPosts() {
    }

    @Test
    void findOne() {
    }
}