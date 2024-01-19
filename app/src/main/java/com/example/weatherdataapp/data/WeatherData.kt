package com.example.weatherdataapp.data

data class WeatherData(
    val id: Int,
    val city: String,
    val country: String,
    val temperature: Int,
    val skyType: String,
    val weatherIcon: String,
)