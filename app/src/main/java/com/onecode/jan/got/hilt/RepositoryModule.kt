package com.onecode.jan.got.hilt

import com.onecode.jan.got.api.IceAndFireApiService
import com.onecode.jan.got.repository.HouseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideHouseRepository(apiService: IceAndFireApiService): HouseRepository = HouseRepository(apiService)
}