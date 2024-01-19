package com.example.weatherdataapp.ui.weather

data class WeatherListViewStates(
    val id: Int,
    val location: String,
    val temperature: String,
    val skyType: String,
    val weatherIcon: String,
)