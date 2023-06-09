package com.boardPractice.demo.controller;

import com.boardPractice.demo.domain.User;
import com.boardPractice.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<User> updatePatchUser(@PathVariable("userId") int userId, @RequestBody User updatedUser) throws JsonProcessingException {
        Optional<User> optionalUser = userService.findOne(userId);

        if(optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            if (updatedUser.getNickname() != null) {
                existingUser.setNickname(updatedUser.getNickname());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            if (updatedUser.getLocation() != null) {
                String location = updatedUser.getLocation();

                String internalUrl = "http://localhost:8008/util/getAddressApi?currentPage=" + "1" +
                        "&countPerPage=" + "10" +
                        "&resultType=" + "json" +
                        "&confmKey=" + "devU01TX0FVVEgyMDIzMDUyODE0NTczMTExMzgwNjk=" +
                        "&keyword=" + location;//URLEncoder.encode(keyword, "UTF-8");

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseLocationValid = restTemplate.getForEntity(internalUrl, String.class);

                if(responseLocationValid.getStatusCode() == HttpStatus.OK){
                    String responseData = responseLocationValid.getBody();
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(responseData);

                    logger.warn(String.valueOf(jsonNode.get("results").get("juso").size()));
                    //주소 valid 체크
                    if(jsonNode.get("results").get("juso").size()>0){
                        logger.warn(jsonNode.get("results").get("juso").get(0).get("roadAddr").asText());
                        existingUser.setLocation(jsonNode.get("results").get("juso").get(0).get("roadAddr").asText());
                    }
                }else{
                    throw new RuntimeException("Internal API call failed");
                }

            }

            Optional<User> savedUser = userService.updateUser(userId, existingUser);

            return ResponseEntity.ok(savedUser.get());
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

    @GetMapping("/auth/kakao")
    public String kakaoLogIn(){
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://kauth.kakao.com/oauth/authorize?client_id=50bc6e2978af614681813952d0053556&redirect_uri=https://umcpractice.shop/api/users/oauth/kakao&response_type=code";

        ResponseEntity<String> authCode = restTemplate.getForEntity(apiUrl, String.class);

        return "redirect:" + apiUrl;
    }


    @GetMapping("/oauth/kakao")
    @ResponseBody
    public ResponseEntity<String> getKakaoAccessToken(@RequestParam("code") String code){
        try{
            String apiUrl = "https://kauth.kakao.com/oauth/token";

            HttpHeaders headers = new HttpHeaders();
            //headers.add("Conent-type","application/x-www-form-urlencoded;charset=utf-8");
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", "authorization_code");
            requestBody.add("client_id", "50bc6e2978af614681813952d0053556");
            requestBody.add("redirect_uri", "https://umcpractice.shop/api/users/oauth/kakao");
            requestBody.add("code", code);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            //API 호출
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,requestEntity, String.class);

            String tokenJson = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(tokenJson);
            String accessToken = String.valueOf(jsonNode.get("access_token"));

            return ResponseEntity.status(500).body(accessToken);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }


}
