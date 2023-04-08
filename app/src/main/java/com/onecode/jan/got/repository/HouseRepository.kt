package com.onecode.jan.got.repository

import com.onecode.jan.got.model.ApiHouse
import com.onecode.jan.got.util.RetrofitUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HouseRepository {

    private val _stateFlow = MutableStateFlow<HouseState>(HouseState.Loading)
    val stateFlow: StateFlow<HouseState> = _stateFlow

    suspend fun fetchHouses() {
        val service = RetrofitUtil.getIceAndFireApiService(GOT_BASE_URL)
        val response = service.fetchHouses()

        _stateFlow.value = if (response.isSuccessful) {
            response.body()?.let {
                HouseState.Success(data = it)
            } ?: HouseState.Error(error = Exception("Empty list"))
        } else {
            HouseState.Error(error = Exception("Failed to fetch houses: ${response.code()} - ${response.message()}"))
        }
    }

    suspend fun fetchHouseById(id: Int) {
        val service = RetrofitUtil.getIceAndFireApiService(GOT_BASE_URL)
        val response = service.fetchHouseById(id)

        _stateFlow.value = if (response.isSuccessful) {
            response.body()?.let {
                HouseState.Success(data = listOf(it))
            } ?: HouseState.Error(error = Exception("Empty list"))
        } else {
            HouseState.Error(error = Exception("Failed to fetch houses: ${response.code()} - ${response.message()}"))
        }
    }

    companion object {
        private const val GOT_BASE_URL = "https://anapioficeandfire.com/"
    }
}

sealed interface HouseState {
    data class Success(val data: List<ApiHouse>) : HouseState
    data class Error(val error: Exception) : HouseState
    object Loading : HouseState
}