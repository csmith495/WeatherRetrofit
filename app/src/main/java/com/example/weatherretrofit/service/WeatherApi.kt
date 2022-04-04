package com.example.weatherretrofit.service

import com.example.weatherretrofit.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather?&units=imperial&appid=APP_KEY_HERE")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>
}