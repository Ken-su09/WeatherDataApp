package com.example.weatherdataapp.ui.weather

import android.app.Application
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.weatherdataapp.R
import com.example.weatherdataapp.databinding.ActivityWeatherBinding
import com.example.weatherdataapp.domain.weather.ClearWeatherDataUseCase
import com.example.weatherdataapp.domain.weather.GetWeatherDataListFlowUseCase
import com.example.weatherdataapp.domain.weather.SetWeatherDataListFlowUseCase
import com.example.weatherdataapp.utils.Constants.Companion.listOfCities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherDataListFlowUseCase: GetWeatherDataListFlowUseCase,
    private val setWeatherDataListFlowUseCase: SetWeatherDataListFlowUseCase,
    private val clearWeatherDataUseCase: ClearWeatherDataUseCase,
    private val application: Application
) : ViewModel() {

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _seekbarDataViewState = MutableLiveData<SeekbarDataViewState>()
    val seekbarDataViewState: LiveData<SeekbarDataViewState> = _seekbarDataViewState

    private var isDownloading = true

    val weatherListViewStatesLiveData = liveData {
        getWeatherDataListFlowUseCase.invoke().collect { list ->
            emit(list.map { weatherData ->
                WeatherListViewStates(
                    id = weatherData.id,
                    location = application.getString(R.string.weather_location, weatherData.city, weatherData.country),
                    temperature = application.getString(R.string.temperature_with_arg, weatherData.temperature),
                    skyType = weatherData.skyType,
                    weatherIcon = weatherData.weatherIcon
                )
            })
        }
    }

    private fun startMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            var counter = 0
            val messages = application.resources.getStringArray(R.array.messages_array).toList()
            while (isDownloading) {
                withContext(Dispatchers.Main) {
                    _messageLiveData.value = messages[counter]
                }
                delay(6000L)
                counter++

                if (counter == messages.size) {
                    counter = 0
                }
            }
        }
    }

    private fun setWeatherData() = viewModelScope.launch {
        for (cityLatLng in listOfCities()) {
            setWeatherDataListFlowUseCase.invoke(cityLatLng.lat, cityLatLng.long)
            delay(10000L)
        }
    }

    fun updateProgressBar() {
        CoroutineScope(Dispatchers.IO).launch {
            isDownloading = true
            startMessages()
            setWeatherData()
            val durationInSeconds = 60
            val numSteps = 10

            val stepInterval = durationInSeconds / numSteps

            var seekbarProgress = 0
            var seekbarTextColor = R.color.purple
            var seekbarVisibility = View.VISIBLE
            var seekbarTextVisibility = View.VISIBLE
            var restartButtonVisibility = View.GONE
            var listOfWeatherVisibility = View.INVISIBLE
            var progressBarVisibility = View.VISIBLE

            for (step in 1..numSteps) {
                Log.i("GetCounter", "numSteps : $numSteps")
                withContext(Dispatchers.Main) {
                    if (seekbarProgress > 80) {
                        seekbarTextColor = R.color.black
                    }

                    _seekbarDataViewState.value = SeekbarDataViewState(
                        seekbarProgress = seekbarProgress,
                        seekbarText = application.getString(R.string.seekbar_progress_with_arg, seekbarProgress),
                        seekbarTextColor = application.getColor(seekbarTextColor),
                        seekbarVisibility = seekbarVisibility,
                        seekbarTextVisibility = seekbarTextVisibility,
                        restartButtonVisibility = restartButtonVisibility,
                        listOfWeatherVisibility = listOfWeatherVisibility,
                        progressBarVisibility = progressBarVisibility
                    )
                }
                seekbarProgress = (step * 100 / numSteps)

                delay(stepInterval * 1000L)
            }

            withContext(Dispatchers.Main) {
                isDownloading = false
                seekbarProgress = 100

                seekbarVisibility = View.INVISIBLE
                seekbarTextVisibility = View.GONE
                restartButtonVisibility = View.VISIBLE
                listOfWeatherVisibility = View.VISIBLE
                progressBarVisibility = View.GONE

                _messageLiveData.value = application.getString(R.string.final_message)

                _seekbarDataViewState.value = SeekbarDataViewState(
                    seekbarProgress = seekbarProgress,
                    seekbarText = application.getString(R.string.seekbar_progress_with_arg, seekbarProgress),
                    seekbarTextColor = application.getColor(seekbarTextColor),
                    seekbarVisibility = seekbarVisibility,
                    seekbarTextVisibility = seekbarTextVisibility,
                    restartButtonVisibility = restartButtonVisibility,
                    listOfWeatherVisibility = listOfWeatherVisibility,
                    progressBarVisibility = progressBarVisibility,
                )
            }
        }
    }

    fun clearData() {
        clearWeatherDataUseCase.invoke()
    }
}