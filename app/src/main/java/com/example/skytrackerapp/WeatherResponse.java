package com.example.skytrackerapp;

public class WeatherResponse {

    public Results results;

    public static class Results {
        public String city;
        public int temp;           // temperatura como inteiro
        public String description; // condição do tempo (Ensolarado, Nublado etc.)
    }
}
