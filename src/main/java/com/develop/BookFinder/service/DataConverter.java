package com.develop.BookFinder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class DataConverter implements IDataConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
