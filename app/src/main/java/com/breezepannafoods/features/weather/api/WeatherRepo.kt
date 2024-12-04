package com.breezepannafoods.features.weather.api

import com.breezepannafoods.base.BaseResponse
import com.breezepannafoods.features.task.api.TaskApi
import com.breezepannafoods.features.task.model.AddTaskInputModel
import com.breezepannafoods.features.weather.model.ForeCastAPIResponse
import com.breezepannafoods.features.weather.model.WeatherAPIResponse
import io.reactivex.Observable

class WeatherRepo(val apiService: WeatherApi) {
    fun getCurrentWeather(zipCode: String): Observable<WeatherAPIResponse> {
        return apiService.getTodayWeather(zipCode)
    }

    fun getWeatherForecast(zipCode: String): Observable<ForeCastAPIResponse> {
        return apiService.getForecast(zipCode)
    }
}