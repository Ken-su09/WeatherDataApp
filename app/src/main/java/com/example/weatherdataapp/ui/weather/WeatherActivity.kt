package com.example.weatherdataapp.ui.weather

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherdataapp.databinding.ActivityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherViewModel.updateProgressBar()

        val listAdapter = WeatherListAdapter()
        binding.listOfWeather.adapter = listAdapter

        weatherViewModel.seekbarDataViewState.observe(this) {
            it?.let { seekbarDataViewState ->
                binding.seekBar.progress = seekbarDataViewState.seekbarProgress
                binding.seekBarText.text = seekbarDataViewState.seekbarText
                binding.seekBarText.setTextColor(seekbarDataViewState.seekbarTextColor)

                binding.seekBar.visibility = seekbarDataViewState.seekbarVisibility
                binding.seekBarText.visibility = seekbarDataViewState.seekbarTextVisibility
                binding.restart.visibility = seekbarDataViewState.restartButtonVisibility
                binding.listOfWeather.visibility = seekbarDataViewState.listOfWeatherVisibility
                binding.progressBar.visibility = seekbarDataViewState.progressBarVisibility
            }
        }

        weatherViewModel.weatherListViewStatesLiveData.observe(this) { listOfWeather ->
            listAdapter.submitList(listOfWeather)
        }

        weatherViewModel.messageLiveData.observe(this) {
            it?.let { message ->
                binding.message.text = message
            }
        }

        binding.restart.setOnClickListener {
            weatherViewModel.clearData()
            listAdapter.submitList(null)
            weatherViewModel.updateProgressBar()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherViewModel.clearData()
    }
}