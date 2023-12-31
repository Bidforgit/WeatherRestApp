package com.anuar.weatherendpoints.db.repository;


import com.anuar.weatherendpoints.db.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Long> {

    Optional<Weather> findByCity(String city);
}
