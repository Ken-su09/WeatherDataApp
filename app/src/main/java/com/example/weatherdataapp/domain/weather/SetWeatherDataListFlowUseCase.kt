package com.example.weatherdataapp.domain.weather

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetWeatherDataListFlowUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend fun invoke(lat: String, long: String){
        weatherRepository.setWeatherDataList(lat, long)
    }
}