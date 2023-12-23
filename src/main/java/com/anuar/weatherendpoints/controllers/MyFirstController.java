package com.anuar.weatherendpoints.controllers;

import com.anuar.weatherendpoints.db.entity.Weather;
import com.anuar.weatherendpoints.dto.MResponse;
import com.anuar.weatherendpoints.dto.MWeather;
import com.anuar.weatherendpoints.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyFirstController {


    private final WeatherService weatherService;

    @GetMapping("/testFirstEndpoint")
    public MResponse testEndpoint() {
        return MResponse.builder()
                .response("tested first endpoint succesfuly")
                .status("200")
                .build();
    }

    @GetMapping("/getWeather")
    public MWeather getWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }


}
