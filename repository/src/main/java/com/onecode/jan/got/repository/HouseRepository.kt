package com.onecode.jan.got.repository

import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.datasource.network.HouseNetworkDatasource
import com.onecode.jan.got.model.api.ApiHouse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HouseRepository(private val networkDatasource: HouseNetworkDatasource) {

    private val _stateFlow =
        MutableStateFlow<RepositoryState<List<ApiHouse>>>(RepositoryState.Loading)
    val stateFlow: StateFlow<RepositoryState<List<ApiHouse>>> = _stateFlow

    suspend fun fetchHouses() {
        _stateFlow.value = RepositoryState.Loading
        _stateFlow.value = when (val result = networkDatasource.fetchHouses()) {
            is DatasourceResult.Success -> RepositoryState.Success(result.data)
            is DatasourceResult.Error -> RepositoryState.Error(result.error)
        }
    }

    suspend fun fetchHouseById(id: Int) {
        _stateFlow.value = RepositoryState.Loading
        _stateFlow.value = when (val result = networkDatasource.fetchHouseById(id)) {
            is DatasourceResult.Success -> RepositoryState.Success(listOf(result.data))
            is DatasourceResult.Error -> RepositoryState.Error(result.error)
        }
    }
}