package com.onecode.jan.got.datasource.hilt

import com.onecode.jan.got.datasource.api.IceAndFireApiService
import com.onecode.jan.got.datasource.network.HouseNetworkDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatasourceModule {

    @Singleton
    @Provides
    fun provideHouseNetworkDatasource(apiService: IceAndFireApiService): HouseNetworkDatasource =
        HouseNetworkDatasource(apiService)
}