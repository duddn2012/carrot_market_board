package com.boardPractice.demo.controller;

import com.boardPractice.demo.domain.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

@Controller
@RequestMapping("/util")
public class UtilController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/getAddressApi")
    @ResponseBody
    public ResponseEntity<String> getAddrApi(
            @RequestParam("currentPage") int currentPage,
            @RequestParam("countPerPage") int countPerPage,
            @RequestParam("resultType") String resultType,
            @RequestParam("confmKey") String confmKey,
            @RequestParam("keyword") String keyword){
        try{
            String apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=" + currentPage +
                    "&countPerPage=" + countPerPage +
                    "&resultType=" + resultType +
                    "&confmKey=" + confmKey +
                    "&keyword=" + keyword;//URLEncoder.encode(keyword, "UTF-8");


            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);

            logger.warn(apiUrl);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }

    }

}
