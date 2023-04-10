package com.onecode.jan.got.datasource.network

import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.datasource.api.IceAndFireApiService
import com.onecode.jan.got.model.api.ApiHouse

class HouseNetworkDatasource(
    private val apiService: IceAndFireApiService
) {
    suspend fun fetchHouses(): DatasourceResult<List<ApiHouse>> {
        val response = apiService.fetchHouses()
        return if (response.isSuccessful) {
            response.body()?.let {
                DatasourceResult.Success(it)
            } ?: DatasourceResult.Error(error = Exception("Empty list"))
        } else {
            DatasourceResult.Error(error = Exception("Failed to fetch houses: ${response.code()} - ${response.message()}"))
        }
    }

    suspend fun fetchHouseById(id: Int): DatasourceResult<ApiHouse> {
        val response = apiService.fetchHouseById(id)
        return if (response.isSuccessful) {
            response.body()?.let {
                DatasourceResult.Success(it)
            } ?: DatasourceResult.Error(error = Exception("Empty list"))
        } else {
            DatasourceResult.Error(error = Exception("Failed to fetch houses: ${response.code()} - ${response.message()}"))
        }
    }
}