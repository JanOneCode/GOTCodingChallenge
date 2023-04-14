package com.onecode.jan.got.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onecode.jan.got.model.domain.DomainHouse
import com.onecode.jan.got.repository.RepositoryState
import com.onecode.jan.got.repository.house.HouseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseDetailViewModel @Inject constructor(
    private val houseRepository: HouseRepository
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<HouseDetailUiState>(HouseDetailUiState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            houseRepository.stateFlow.collectLatest {
                update(it)
            }
        }
    }

    private fun update(state: RepositoryState<DomainHouse>) {
        _uiStateFlow.value = when (state) {
            RepositoryState.Loading -> HouseDetailUiState.Loading
            is RepositoryState.Success -> HouseDetailUiState.Success(
                data = state.data.toUiHouseDetail()
            )
            is RepositoryState.Error -> HouseDetailUiState.Error(error = state.error)
        }
    }

    fun fetchHouseById(id: Int) {
        viewModelScope.launch {
            houseRepository.fetchHouseById(id)
        }
    }

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