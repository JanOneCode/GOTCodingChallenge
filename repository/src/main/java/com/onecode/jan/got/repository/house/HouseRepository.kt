package com.onecode.jan.got.repository.house

import androidx.paging.PagingData
import com.onecode.jan.got.model.api.ApiHouse
import com.onecode.jan.got.repository.RepositoryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HouseRepository {
    val stateFlow: StateFlow<RepositoryState<List<ApiHouse>>>

    suspend fun fetchHouseById(id: Int)
    fun getHouses(): Flow<PagingData<ApiHouse>>
}