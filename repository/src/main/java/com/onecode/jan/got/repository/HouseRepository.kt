package com.onecode.jan.got.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.datasource.network.HouseNetworkDatasource
import com.onecode.jan.got.model.api.ApiHouse
import com.onecode.jan.got.repository.paging.HousesPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class HouseRepository(
    private val networkDatasource: HouseNetworkDatasource
) {
    private val _stateFlow =
        MutableStateFlow<RepositoryState<List<ApiHouse>>>(RepositoryState.Loading)
    val stateFlow: StateFlow<RepositoryState<List<ApiHouse>>> = _stateFlow

    suspend fun fetchHouseById(id: Int) {
        _stateFlow.value = when (val result = networkDatasource.fetchHouseById(id)) {
            is DatasourceResult.Success -> RepositoryState.Success(listOf(result.data))
            is DatasourceResult.Error -> RepositoryState.Error(result.error)
        }
    }

    fun getHouses() = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = { HousesPagingSource(networkDatasource) }
    ).flow
}