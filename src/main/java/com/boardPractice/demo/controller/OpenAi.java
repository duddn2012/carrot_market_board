package com.boardPractice.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.theokanning.openai.service.OpenAiService.*;

@Controller
@RequestMapping("/OpenAi")
public class OpenAi {
    private final Environment env;
    private static final String BASE_URL = "https://api.openai.com/";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final ObjectMapper mapper = defaultObjectMapper();

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OpenAi(Environment env) {
        this.env = env;
    }


    /*
     * Completion Create
     * @Request Text 클라이언트 대화
     */
    @PostMapping("/completion/create")
    @ResponseBody
    public ResponseEntity<String> createCompletion(){
        try {
            String apiUrl = BASE_URL + "v1/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(env.getProperty("openai.key"));


            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "text-davinci-003");
            requestBody.put("prompt", "Say this is a test");
            requestBody.put("max_tokens", 256);
            requestBody.put("temperature", 0);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            //API 호출
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            return ResponseEntity.ok(responseEntity.getBody());
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }

    }

    @GetMapping("/model/get")
    @ResponseBody
    public ResponseEntity<List<String>> getModel(){
        try {
            String apiUrl = BASE_URL + "v1/models";

            HttpHeaders headers = new HttpHeaders();
            //headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(env.getProperty("openai.key"));

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity requestEntity = new HttpEntity(headers);

            //API 호출
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

            String tokenJson = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(tokenJson);
            JsonNode dataArray = jsonNode.get("data");

            List<String> ids = new ArrayList<>();

            for(JsonNode data : dataArray){
                String id = data.get("id").asText();
                ids.add(id);
            }

            return ResponseEntity.ok(ids);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
