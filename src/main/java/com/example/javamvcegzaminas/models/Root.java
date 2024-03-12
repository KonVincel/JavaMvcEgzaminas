package com.example.javamvcegzaminas.models;

import java.util.ArrayList;

public class Root {
    public Place place;
    public String forecastType;
    public String forecastCreationTimeUtc;
    public ArrayList<ForecastTimestamp> forecastTimestamps;
}