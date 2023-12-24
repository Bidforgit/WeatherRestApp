package com.anuar.weatherendpoints.service;

import com.anuar.weatherendpoints.db.entity.Weather;
import com.anuar.weatherendpoints.db.repository.WeatherRepository;
import com.anuar.weatherendpoints.dto.MResponse;
import com.anuar.weatherendpoints.dto.MWeather;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anuar.weatherendpoints.dto.MWeather.Response;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private RestTemplate restTemplate;

    @Value("${weather.app.apiKey}")
    private String apiKey;
    
    public MWeather getWeather(String city) {
        if(city.equals("")) return null;
        try {
            restTemplate = new RestTemplate();
            String apiURL = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
            ResponseEntity<Response> response = restTemplate.getForEntity(apiURL, Response.class);

            if(response.getStatusCode() == HttpStatus.OK) {
                Response dataResponse = response.getBody();
                log.info("dataResponse {}", dataResponse);
                if(dataResponse != null) return MWeather.create(dataResponse);
            }
        }catch (Exception e) {
            log.error("Exception with 3rd party service {}", e.getMessage());
        }
        return null;
    }

    public MResponse saveWeather(Weather weather) {
        try{
            weatherRepository.save(weather);
        }catch (Exception ex){
            log.error("Exception with {}", ex.getMessage());
        }
        return MResponse.builder()
                .status("200")
                .response("successfully created")
                .build();
    }

    public MResponse updateWeather(Weather weather) {
        try{
            Optional<Weather> dataResponse = weatherRepository.findByCity(weather.getCity());
            if (dataResponse.isPresent()) {
                Weather dataWeather = dataResponse.get();
                dataWeather.setDescription(weather.getDescription());
                dataWeather.setTemperature(weather.getTemperature());
                weatherRepository.save(dataWeather);
            }
        }catch (Exception ex){
            log.error("Exception with {}", ex.getMessage());
        }
        return MResponse.builder()
                .status("200")
                .response("successfully updated")
                .build();
    }

    public MResponse deleteWeather(String city) {
        try{
            Optional<Weather> dataResponse = weatherRepository.findByCity(city);

            dataResponse.ifPresent(weather -> weatherRepository.deleteById(weather.getId()));
        }catch (Exception ex){
            log.error("Exception with {}", ex.getMessage());
        }
        return MResponse.builder()
                .status("200")
                .response("successfully deleted")
                .build();
    }
}
