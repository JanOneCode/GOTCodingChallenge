package com.onecode.jan.got.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onecode.jan.got.model.UiHouseItem
import com.onecode.jan.got.model.api.ApiHouse
import com.onecode.jan.got.repository.HouseRepository
import com.onecode.jan.got.repository.RepositoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HousesViewModel @Inject constructor(
    private val houseRepository: HouseRepository
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<HousesUiState>(HousesUiState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            houseRepository.stateFlow.collectLatest {
                update(it)
            }
        }
    }

    private fun update(state: RepositoryState<List<ApiHouse>>) {
        _uiStateFlow.value = when (state) {
            RepositoryState.Loading -> HousesUiState.Loading
            is RepositoryState.Success -> HousesUiState.Success(data = state.data.toUiHouseItem())
            is RepositoryState.Error -> HousesUiState.Error(error = state.error)
        }
    }

    fun fetchHouses() {
        viewModelScope.launch {
            houseRepository.fetchHouses()
        }
    }

    private fun List<ApiHouse>.toUiHouseItem(): List<UiHouseItem> =
        this.map {
            UiHouseItem(
                id = it.url.parseHouseId(),
                name = it.name,
                region = it.region
            )
        }

    private fun String.parseHouseId(): Int = this.split("/").last().toInt()
}

sealed interface HousesUiState {
    data class Success(val data: List<UiHouseItem>) : HousesUiState
    data class Error(val error: Exception) : HousesUiState
    object Loading : HousesUiState
}