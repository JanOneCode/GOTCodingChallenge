package com.onecode.jan.got.datasource.network

import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.datasource.api.IceAndFireApiService
import retrofit2.Response

class HouseNetworkDatasource(
    private val apiService: IceAndFireApiService
) {
    suspend fun fetchHouses() = apiService.fetchHouses().toDatasourceResult()

    suspend fun fetchHousesByPage(page: Int) =
        apiService.fetchHousesByPage(page).toDatasourceResult()

    suspend fun fetchHouseById(id: Int) = apiService.fetchHouseById(id).toDatasourceResult()

    private fun <T> Response<T>.toDatasourceResult(): DatasourceResult<T> {
        return if (this.isSuccessful) {
            this.body()?.let {
                DatasourceResult.Success(it)
            } ?: DatasourceResult.Error(error = Exception("Empty list"))
        } else {
            DatasourceResult.Error(error = Exception("Failed to fetch houses: ${this.code()} - ${this.message()}"))
        }
    }
}