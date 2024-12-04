package com.breezepannafoods.features.weather.api

import com.breezepannafoods.features.task.api.TaskApi
import com.breezepannafoods.features.task.api.TaskRepo

object WeatherRepoProvider {
    fun weatherRepoProvider(): WeatherRepo {
        return WeatherRepo(WeatherApi.create())
    }
}