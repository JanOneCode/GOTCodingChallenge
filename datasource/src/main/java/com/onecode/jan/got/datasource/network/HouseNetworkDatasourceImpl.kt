package com.onecode.jan.got.datasource.network

import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.datasource.api.IceAndFireApiService
import retrofit2.Response
import javax.inject.Inject

internal class HouseNetworkDatasourceImpl @Inject constructor(
    private val apiService: IceAndFireApiService
) : HouseNetworkDatasource {

    override suspend fun fetchHousesByPage(page: Int) =
        apiService.fetchHousesByPage(page).toDatasourceResult()

    override suspend fun fetchHouseById(id: Int) =
        apiService.fetchHouseById(id).toDatasourceResult()

    private fun <T> Response<T>.toDatasourceResult(): DatasourceResult<T> {
        return if (this.isSuccessful) {
            this.body()?.let {
                DatasourceResult.Success(it)
            } ?: DatasourceResult.Error(error = Exception("Response body is null"))
        } else {
            DatasourceResult.Error(error = Exception("Failed to fetch houses: ${this.code()} - ${this.message()}"))
        }
    }
}