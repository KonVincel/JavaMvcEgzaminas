package com.example.javamvcegzaminas.models;

public class ForecastModel {
    public String dateTime;
    public Double temperature;
    public int wind;

    public int direction;

    public ForecastModel(String dateTime, Double temperature, int wind, int direction ) {

        this.dateTime=dateTime;
        this.temperature=temperature;
        this.wind=wind;
        this.direction=direction;
    }
}
