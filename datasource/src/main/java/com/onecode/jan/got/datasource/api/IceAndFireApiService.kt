package com.onecode.jan.got.datasource.api

import com.onecode.jan.got.model.api.ApiHouse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val PAGE_SIZE = 25

interface IceAndFireApiService {
    @GET("/api/houses")
    suspend fun fetchHouses(): Response<List<ApiHouse>>

    @GET("/api/houses")
    suspend fun fetchHousesByPage(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = PAGE_SIZE
    ): Response<List<ApiHouse>>

    @GET("/api/houses/{id}")
    suspend fun fetchHouseById(@Path("id") id: Int): Response<ApiHouse>
}