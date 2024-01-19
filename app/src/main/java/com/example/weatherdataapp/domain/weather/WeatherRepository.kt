package com.example.weatherdataapp.domain.weather

import com.example.weatherdataapp.data.WeatherData
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {

    suspend fun setWeatherDataList(lat: String, long: String)

    fun getWeatherDataList(): Flow<List<WeatherData>>
}