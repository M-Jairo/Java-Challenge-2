package com.spring.challenge_2.Servicio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class combierteDatos implements ICombierteDatos{
    private ObjectMapper maper = new ObjectMapper();

    @Override
    public <T> T datos(String json, Class<T> clase) {
        try {
            return maper.readValue(json, clase);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
