package com.onecode.jan.got.repository.house

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.datasource.network.HouseNetworkDatasource
import com.onecode.jan.got.model.domain.DomainHouse
import com.onecode.jan.got.model.toDomain
import com.onecode.jan.got.repository.RepositoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class HouseRepositoryImpl @Inject constructor(
    private val networkDatasource: HouseNetworkDatasource
) : HouseRepository {

    private val _stateFlow = MutableStateFlow<RepositoryState<DomainHouse>>(RepositoryState.Loading)
    override val stateFlow: StateFlow<RepositoryState<DomainHouse>>
        get() = _stateFlow

    override suspend fun fetchHouseById(id: Int) {
        _stateFlow.value = when (val result = networkDatasource.fetchHouseById(id)) {
            is DatasourceResult.Success -> RepositoryState.Success(result.data.toDomain())
            is DatasourceResult.Error -> RepositoryState.Error(result.error)
        }
    }

    override fun getHouses() = Pager(
        config = PagingConfig(pageSize = 25, prefetchDistance = 5),
        pagingSourceFactory = { HousesPagingSource(networkDatasource) }
    ).flow
}