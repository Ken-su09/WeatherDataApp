package com.example.weatherdataapp.utils

import com.example.weatherdataapp.ui.weather.CityLatLong

class Constants {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org"

        fun listOfCities() = listOf(
            CityLatLong("48.1173", "-1.6778"),
            CityLatLong("48.8566", "2.3522"),
            CityLatLong("47.2184", "-1.5536"),
            CityLatLong("44.8378", "-0.5792"),
            CityLatLong("45.75", "4.85"),
        )
    }
}