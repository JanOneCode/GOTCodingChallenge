package com.onecode.jan.got.datasource.network

import com.onecode.jan.got.datasource.DatasourceResult
import com.onecode.jan.got.model.api.ApiHouse
import com.onecode.jan.got.model.domain.DomainHouse

interface HouseNetworkDatasource {
    suspend fun fetchHousesByPage(page: Int): DatasourceResult<List<ApiHouse>>
    suspend fun fetchHouseById(id: Int): DatasourceResult<ApiHouse>
}