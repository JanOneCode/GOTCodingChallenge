package com.onecode.jan.got.repository.hilt

import com.onecode.jan.got.datasource.network.HouseNetworkDatasource
import com.onecode.jan.got.repository.HouseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHouseRepository(networkDatasource: HouseNetworkDatasource): HouseRepository =
        HouseRepository(networkDatasource)
}