package com.boardPractice.demo.controller;

import com.boardPractice.demo.domain.User;
import com.boardPractice.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    @ResponseBody
    public int userJoin(@RequestBody User user){
        return userService.join(user);
    }

    @GetMapping("/search")
    @ResponseBody
    public List<User> searchUsers(){
        return userService.findUsers();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") int userId, @RequestBody User updatedUser){
        Optional<User> savedUser = userService.updateUser(userId, updatedUser);

        logger.info("INFO Level LOG");
        logger.warn("Warn Level LOG");
        logger.error("ERROR Level LOG");

        if(savedUser.isPresent()){
            return ResponseEntity.ok(savedUser.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/patch/{userId}")
    public ResponseEntity<User> updatePatchUser(@PathVariable("userId") int userId, @RequestBody User updatedUser) {
        Optional<User> optionalUser = userService.findOne(userId);
        if(optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            if (existingUser.getNickname() != null) {
                existingUser.setNickname(existingUser.getNickname());
            }
            if (existingUser.getEmail() != null) {
                existingUser.setEmail(existingUser.getEmail());
            }
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") int userId){
        Optional<User> optionalUser = userService.findOne(userId);
        if(optionalUser.isPresent()){
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{userId}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable("userId") int userId){
        //사용자 조회 API에서 Optional<User>을 직접 반환하는 대신, ResponseEntity를 사용하여 세부적인 응답을 구성할 수 있는 장점
        //Optional은 값이 없을 수도 있는 상황에서 null 체크를 깔끔하게 처리할 수 있는 자바 8+의 기능
        Optional<User> optionalUser = userService.findOne(userId);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
