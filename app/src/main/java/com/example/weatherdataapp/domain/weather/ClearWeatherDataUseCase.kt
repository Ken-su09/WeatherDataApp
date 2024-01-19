package com.example.weatherdataapp.domain.weather

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearWeatherDataUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    fun invoke() {
        weatherRepository.clearData()
    }
}