package com.boardPractice.demo.controller;

import com.boardPractice.demo.domain.User;
import com.boardPractice.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String hello(Model model){
        return "hello";
    }

    @GetMapping("/apiTest")
    @ResponseBody
    public Optional<User> apiTest(@RequestParam("userId") int userId){
        return userService.findOne(userId);
    }

    @GetMapping("/ex07")
    public ResponseEntity<String> apiTest(){
        String msg="{\"name\":\"홍길동\"}";

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type","application/json;charset=UTF-8");

        return new ResponseEntity<>(msg, header, HttpStatus.OK);
    }
}
