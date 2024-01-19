package com.example.weatherdataapp.domain.weather

import android.util.Log
import com.example.weatherdataapp.api.WeatherApiService
import com.example.weatherdataapp.data.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class WeatherRepositoryImpl @Inject constructor(private val weatherApiService: WeatherApiService) : WeatherRepository {

    private val weatherDataListFlow = MutableStateFlow<List<WeatherData>>(emptyList())

    override suspend fun setWeatherDataList(lat: String, long: String) {
        val currentWeatherResponse = try {
            weatherApiService.getNearbyPlacesResponse(lat, long)
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (e: Exception) {
            Log.i("GetCity", "e : $e")
            e.printStackTrace()
            null
        }

        val weatherData = if (currentWeatherResponse != null) {
            WeatherData(
                id = currentWeatherResponse.id,
                city = currentWeatherResponse.name,
                country = currentWeatherResponse.sys.country,
                temperature = currentWeatherResponse.main.temp.toInt(),
                skyType = currentWeatherResponse.weather.first().description,
                weatherIcon = "https://openweathermap.org/img/wn/${currentWeatherResponse.weather.first().icon}@2x.png"
            )
        } else {
            WeatherData(
                id = 0, city = "", country = "", temperature = 0, skyType = "", weatherIcon = ""
            )
        }

        weatherDataListFlow.update { currentList ->
            currentList + weatherData
        }
    }

    override fun getWeatherDataList(): Flow<List<WeatherData>> = weatherDataListFlow

    override fun clearData() {
        weatherDataListFlow.update {
            emptyList()
        }
    }
}