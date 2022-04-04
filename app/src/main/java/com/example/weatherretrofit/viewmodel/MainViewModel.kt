package com.example.weatherretrofit.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherretrofit.model.WeatherModel
import com.example.weatherretrofit.service.WeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "MainViewModel"

class MainViewModel: ViewModel() {

    private val weatherService = WeatherService()
    private val disposable = CompositeDisposable()

    val weather_data = MutableLiveData<WeatherModel>()
    val weather_error = MutableLiveData<Boolean>()
    val weather_load = MutableLiveData<Boolean>()

    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)

    }

    private fun getDataFromAPI(cityName: String) {

        weather_load.value = true
        disposable.add(
            weatherService.getDataService(cityName )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {
                    override fun onSuccess(t: WeatherModel) {
                        weather_data.value = t
                        weather_error.value = false
                        weather_load.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weather_error.value = true
                        weather_load.value = false
                        Log.e(TAG, "onError: $e")
                    }

                })
        )
    }
}