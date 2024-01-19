package com.example.weatherdataapp.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherdataapp.databinding.ItemWeatherBinding

class WeatherListAdapter : ListAdapter<WeatherListViewStates, WeatherListAdapter.ViewHolder>(WeatherListViewStatesComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(weatherListViewStates: WeatherListViewStates) {
            Glide.with(binding.weatherIcon).load(weatherListViewStates.weatherIcon).into(binding.weatherIcon)
            binding.weatherCity.text = weatherListViewStates.location
            binding.weatherTemperature.text = weatherListViewStates.temperature
            binding.weatherDescription.text = weatherListViewStates.skyType
        }
    }

    object WeatherListViewStatesComparator : DiffUtil.ItemCallback<WeatherListViewStates>() {
        override fun areItemsTheSame(oldItem: WeatherListViewStates, newItem: WeatherListViewStates): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherListViewStates, newItem: WeatherListViewStates): Boolean {
            return oldItem == newItem
        }
    }
}