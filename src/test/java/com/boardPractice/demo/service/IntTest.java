package com.boardPractice.demo.service;

import com.boardPractice.demo.domain.User;
import com.boardPractice.demo.repository.JpaUserRepository;
import com.boardPractice.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class IntTest {
    @Autowired UserRepository userRepository;
    @Autowired UserService userService;

    @Test
    void join() {
        //given
        User user = new User();
        user.setNickname("test0");
        user.setPhone("01011112222");
        user.setManner(0);
        user.setBadge("badge");
        user.setLocation("Seoul");
        user.setEmail("test@naver.com");

        //when
        int saveId = userService.join(user);

        //then
        User findUser = userService.findOne(saveId).get();
        assertThat(user.getNickname()).isEqualTo(findUser.getNickname());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        User user1 = new User();
        user1.setNickname("spring");


        User user2 = new User();
        user2.setNickname("spring");
        //when
        userService.join(user1);
        userService.join(user2);
        //then

    }

    @Test
    void findUsers() {
    }

    @Test
    void findOne() {
    }
}