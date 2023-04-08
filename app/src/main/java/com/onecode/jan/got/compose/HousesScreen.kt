package com.onecode.jan.got.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.onecode.jan.got.model.UiHouseItem
import com.onecode.jan.got.ui.theme.IceAndFireTheme
import com.onecode.jan.got.util.PhonePreview
import com.onecode.jan.got.viewmodel.HousesUiState
import com.onecode.jan.got.viewmodel.HousesViewModel

@Composable
fun HousesScreen(
    onClick: (Int) -> Unit,
    viewModel: HousesViewModel = viewModel()
) {
    val state = viewModel.uiStateFlow.collectAsState()

    viewModel.fetchHouses()

    Content(
        state = state.value,
        onClick = onClick
    )
}

@Composable
private fun Content(
    state: HousesUiState,
    onClick: (Int) -> Unit
) {
    Column {
        when (state) {
            is HousesUiState.Error -> {}
            HousesUiState.Loading -> {}
            is HousesUiState.Success -> {
                LazyColumn {
                    state.data.forEach {
                        item {
                            HouseCard(house = it, onClick = onClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HouseCard(
    house: UiHouseItem,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Black),
                shape = RoundedCornerShape(size = 2.dp)
            )
            .clickable { onClick(house.id) }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 4.dp),
            text = house.name
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 8.dp),
            text = house.region
        )
    }
}

//region Preview
@Composable
@PhonePreview
private fun PreviewScreen() = IceAndFireTheme {
    Content(
        state = getMockData(),
        onClick = {}
    )
}

@Composable
@PhonePreview
private fun PreviewItem() = IceAndFireTheme {
    HouseCard(house = houseStark, onClick = {})
}

private val houseStark = UiHouseItem(id = 1, name = "House Stark", region = "The North")
private val houseAmbrose = UiHouseItem(id = 2, name = "House Ambrose", region = "The Reach")
private val houseAshwood = UiHouseItem(id = 3, name = "House Ashwood", region = "The North")

private fun getMockData() = HousesUiState.Success(
    data = listOf(houseStark, houseAmbrose, houseAshwood)
)
//endregion