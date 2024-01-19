package com.example.weatherdataapp.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherdataapp.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}