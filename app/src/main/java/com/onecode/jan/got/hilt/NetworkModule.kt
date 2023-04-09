package com.onecode.jan.got.hilt

import com.onecode.jan.got.api.IceAndFireApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val ICE_AND_FIRE_BASE_URL = "https://anapioficeandfire.com/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ICE_AND_FIRE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideIceAndFireApiService(retrofit: Retrofit): IceAndFireApiService {
        return retrofit.create(IceAndFireApiService::class.java)
    }
}