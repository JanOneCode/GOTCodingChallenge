package com.onecode.jan.got.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.onecode.jan.got.model.UiHouseItem
import com.onecode.jan.got.model.api.ApiHouse
import com.onecode.jan.got.repository.HouseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HouseOverviewViewModel @Inject constructor(
    private val houseRepository: HouseRepository
) : ViewModel() {

    fun getHouses(): Flow<PagingData<UiHouseItem>> =
        houseRepository.getHouses()
            .map { pagingData -> pagingData.map { it.toUiHouseItem() } }
            .cachedIn(viewModelScope)

    private fun ApiHouse.toUiHouseItem(): UiHouseItem =
        UiHouseItem(
            id = this.url.parseHouseId(),
            name = this.name,
            region = this.region
        )

    private fun String.parseHouseId(): Int = this.split("/").last().toInt()
}