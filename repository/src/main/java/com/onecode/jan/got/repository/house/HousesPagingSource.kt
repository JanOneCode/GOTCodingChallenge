package com.onecode.jan.got.repository.house

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.datasource.network.HouseNetworkDatasource
import com.onecode.jan.got.model.domain.DomainHouse
import com.onecode.jan.got.model.toDomain

internal class HousesPagingSource(
    private val networkDatasource: HouseNetworkDatasource
) : PagingSource<Int, DomainHouse>() {

    override fun getRefreshKey(state: PagingState<Int, DomainHouse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainHouse> {
        val page = params.key ?: 1
        return when (val response = networkDatasource.fetchHousesByPage(page)) {
            is DatasourceResult.Error -> LoadResult.Error(response.error)
            is DatasourceResult.Success -> LoadResult.Page(
                data = response.data.map { it.toDomain() },
                prevKey = null,
                nextKey = if (response.data.isEmpty()) null else page.plus(1),
            )
        }
    }
}