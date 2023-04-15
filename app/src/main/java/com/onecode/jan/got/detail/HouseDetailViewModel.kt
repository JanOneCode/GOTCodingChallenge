package com.onecode.jan.got.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onecode.jan.got.model.domain.DomainHouse
import com.onecode.jan.got.navigation.HOUSE_ID
import com.onecode.jan.got.repository.RepositoryResult
import com.onecode.jan.got.repository.house.HouseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseDetailViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val houseRepository: HouseRepository
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<HouseDetailUiState>(HouseDetailUiState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        fetchHouseById(state.get<Int>(HOUSE_ID)!!)
    }

    private fun fetchHouseById(id: Int) = viewModelScope.launch {
        _uiStateFlow.value = HouseDetailUiState.Loading
        _uiStateFlow.value = when (val result = houseRepository.fetchHouseById(id)) {
            is RepositoryResult.Error -> HouseDetailUiState.Error(error = result.error)
            is RepositoryResult.Success -> HouseDetailUiState.Success(
                data = result.data.toUiHouseDetail()
            )
        }
    }

    fun retry() = fetchHouseById(state.get<Int>(HOUSE_ID)!!)

    private fun DomainHouse.toUiHouseDetail(): UiHouseDetail =
        UiHouseDetail(
            name = this.name,
            region = this.region,
            coatOfArms = this.coatOfArms,
            words = this.words,
            titles = this.titles,
            seats = this.seats,
            founded = this.founded,
            diedOut = this.diedOut,
            ancestralWeapons = this.ancestralWeapons,
        )
}

sealed interface HouseDetailUiState {
    data class Success(val data: UiHouseDetail) : HouseDetailUiState
    data class Error(val error: Exception) : HouseDetailUiState
    object Loading : HouseDetailUiState
}