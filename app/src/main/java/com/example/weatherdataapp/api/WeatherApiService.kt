package com.example.weatherdataapp.api

import com.example.weatherdataapp.BuildConfig
import com.example.weatherdataapp.data.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("/data/2.5/weather?units=metric&lang=FR" + "&appid=" + BuildConfig.API_KEY)
    suspend fun getNearbyPlacesResponse(@Query("lat") lat: String, @Query("lon") lon: String): CurrentWeatherResponse
}