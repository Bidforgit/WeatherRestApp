package com.anuar.weatherendpoints.dto;

import com.anuar.weatherendpoints.db.entity.Weather;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class MWeather {

    private String description;

    private double temperature;

    private String city;

    public static MWeather create(Response response) {
        return MWeather.builder()
                .city(response.getName())
                .description(response.getWeathers().get(0).getDescription())
                .temperature(response.getMain().getTemp())
                .build();
    }

    @Getter
    @Setter
    @ToString
    public static class Response {
        private Main main;
        private List<Weather> weathers;
        private String name;

        @Getter
        @Setter
        @ToString
        public static class Main{
            private double temp;
        }
    }
}
