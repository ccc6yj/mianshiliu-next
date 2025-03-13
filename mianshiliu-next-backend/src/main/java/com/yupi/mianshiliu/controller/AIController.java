package com.yupi.mianshiliu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AIController {
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "";
    @PostMapping("/ask")
    public ResponseEntity<String> askAI(@RequestBody String question) throws JsonProcessingException {

        // 调用AI模型处理问题
        String response = callAIModel(question);
        return ResponseEntity.ok(response);
    }

    private String callAIModel(String question) throws JsonProcessingException {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

// 使用 Jackson 构建请求体
        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("content", question);
        systemMessage.put("role", "system");
        messages.add(systemMessage);

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("content", "Hi");
        userMessage.put("role", "user");
        messages.add(userMessage);

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("messages", messages);
        //R1模型deepseek-chat v3模型model='deepseek-chat'
        requestBodyMap.put("model", "deepseek-chat");
        requestBodyMap.put("frequency_penalty", 0);
        requestBodyMap.put("max_tokens", 2048);
        requestBodyMap.put("presence_penalty", 0);
        requestBodyMap.put("response_format", org.elasticsearch.core.Map.of("type", "text"));
        requestBodyMap.put("stop", null);
        requestBodyMap.put("stream", false);
        requestBodyMap.put("stream_options", null);
        requestBodyMap.put("temperature", 1);
        requestBodyMap.put("top_p", 1);
        requestBodyMap.put("tools", null);
        requestBodyMap.put("tool_choice", "none");
        requestBodyMap.put("logprobs", false);
        requestBodyMap.put("top_logprobs", null);

        String requestBody = objectMapper.writeValueAsString(requestBodyMap);

        // 发送请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        // 解析响应
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return "Error calling AI API";
        }
    }
}