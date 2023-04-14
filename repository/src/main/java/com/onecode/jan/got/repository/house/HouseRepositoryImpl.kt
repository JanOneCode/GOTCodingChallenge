package com.onecode.jan.got.repository.house

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.datasource.network.HouseNetworkDatasource
import com.onecode.jan.got.model.api.ApiHouse
import com.onecode.jan.got.repository.RepositoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class HouseRepositoryImpl @Inject constructor(
    private val networkDatasource: HouseNetworkDatasource
) : HouseRepository {

    private val _stateFlow =
        MutableStateFlow<RepositoryState<List<ApiHouse>>>(RepositoryState.Loading)
    override val stateFlow: StateFlow<RepositoryState<List<ApiHouse>>>
        get() = _stateFlow

    override suspend fun fetchHouseById(id: Int) {
        _stateFlow.value = when (val result = networkDatasource.fetchHouseById(id)) {
            is DatasourceResult.Success -> RepositoryState.Success(listOf(result.data))
            is DatasourceResult.Error -> RepositoryState.Error(result.error)
        }
    }

    override fun getHouses() = Pager(
        config = PagingConfig(pageSize = 25, prefetchDistance = 5),
        pagingSourceFactory = { HousesPagingSource(networkDatasource) }
    ).flow
}