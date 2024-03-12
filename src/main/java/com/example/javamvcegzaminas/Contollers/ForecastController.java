package com.example.javamvcegzaminas.Contollers;

import com.example.javamvcegzaminas.models.ForecastModel;
import com.example.javamvcegzaminas.models.IndexModel;
import com.example.javamvcegzaminas.models.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

@Controller
public class ForecastController {

    @GetMapping("/")

    public ModelAndView index(@RequestParam(required = false) String city) throws IOException {

        ModelAndView modelAndView = new ModelAndView("index");
        var indexModel = new IndexModel();
        indexModel.city=city;

        var forecasts = getForecasts(city);
        indexModel.forecasts=forecasts;

        modelAndView.addObject("indexModel", indexModel);
        return modelAndView;
    }

    public static String GetMeteoForercastsJson(String city) throws IOException {

        URL url = new URL("https://api.meteo.lt/v1/places/"+city+"/forecasts/long-term");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        System.out.println(responsecode);
        String text = "";
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            text += scanner.nextLine();
        }
        scanner.close();
        return text;
    }

    private static Root GetObjectFromJson(String json) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Root meteoObj = om.readValue(json, Root.class);
        return meteoObj;
    }

    private static ArrayList<ForecastModel> getForecasts(String city) throws JsonProcessingException, IOException {
        var forecasts = new ArrayList<ForecastModel>();
        if (city!=null && !city.equals("")) {
            var meteoForecastsJson = GetMeteoForercastsJson(city);
            Root meteoObj = GetObjectFromJson(meteoForecastsJson );
            for (var item : meteoObj.forecastTimestamps) {
                var row = new ForecastModel(item.forecastTimeUtc, item.airTemperature, item.windSpeed, item.windDirection);
                forecasts.add(row);
            }
        }
        return forecasts;
    }

}


