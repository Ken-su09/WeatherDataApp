package com.example.weatherdataapp.ui.weather

data class SeekbarDataViewState(
    val seekbarProgress: Int,
    val seekbarText: String,
    val seekbarTextColor: Int,
    val seekbarVisibility: Int,
    val seekbarTextVisibility: Int,
    val restartButtonVisibility: Int,
    val listOfWeatherVisibility: Int,
    val progressBarVisibility: Int,
)