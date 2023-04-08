package com.onecode.jan.got.util

import com.onecode.jan.got.api.IceAndFireApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {
    private fun getInstance(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getIceAndFireApiService(baseUrl: String): IceAndFireApiService =
        getInstance(baseUrl)
            .create(IceAndFireApiService::class.java)
}