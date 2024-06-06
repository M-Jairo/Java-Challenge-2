package com.spring.challenge_2.Servicio;

public interface ICombierteDatos {
    <T> T datos(String json, Class<T> clase);
}
