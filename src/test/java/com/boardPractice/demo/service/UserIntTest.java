package com.boardPractice.demo.service;

import com.boardPractice.demo.domain.User;
import com.boardPractice.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserIntTest {
    @Autowired UserRepository userRepository;
    @Autowired UserService userService;

    @Test
    void join() {
        //given
        List<User> users = new ArrayList<>();

        for(int i=0;i<10;i++) {
            User user = new User();
            user.setNickname("test"+i);
            user.setPhone("0101111222"+i);
            user.setManner(36);
            user.setBadge("badge");
            user.setLocation("Seoul");
            user.setPassword("1234");
            user.setEmail("test"+i+"@naver.com");

            users.add(user);
        }

        //when
        List<Integer> saveIds = new ArrayList<>();
        for(int i=0;i<10;i++){
            int saveId = userService.join(users.get(i));
            saveIds.add(saveId);
        }

        //then
        for(int i=0;i<10;i++){
            User findUser = userService.findOne(saveIds.get(i)).get();
            assertThat(users.get(i).getNickname()).isEqualTo(findUser.getNickname());
        }
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