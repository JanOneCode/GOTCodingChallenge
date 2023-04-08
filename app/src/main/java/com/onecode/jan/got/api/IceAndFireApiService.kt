package com.onecode.jan.got.api

import com.onecode.jan.got.model.ApiHouse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IceAndFireApiService {
    @GET("/api/houses")
    suspend fun fetchHouses(): Response<List<ApiHouse>>

    @GET("/api/houses/{id}")
    suspend fun fetchHouseById(@Path("id") id: Int): Response<ApiHouse>
}