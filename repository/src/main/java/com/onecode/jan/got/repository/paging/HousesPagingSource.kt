package com.onecode.jan.got.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.datasource.network.HouseNetworkDatasource
import com.onecode.jan.got.model.api.ApiHouse

internal class HousesPagingSource(
    private val networkDatasource: HouseNetworkDatasource
) : PagingSource<Int, ApiHouse>() {

    override fun getRefreshKey(state: PagingState<Int, ApiHouse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiHouse> {
        val page = params.key ?: 1
        return when (val response = networkDatasource.fetchHousesByPage(page)) {
            is DatasourceResult.Error -> LoadResult.Error(response.error)
            is DatasourceResult.Success -> LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = if (response.data.isEmpty()) null else page.plus(1),
            )
        }
    }
}