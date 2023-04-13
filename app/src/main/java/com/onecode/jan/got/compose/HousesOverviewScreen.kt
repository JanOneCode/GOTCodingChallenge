package com.onecode.jan.got.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.onecode.jan.got.model.UiHouseItem
import com.onecode.jan.got.designsystem.theme.IceAndFireTheme
import com.onecode.jan.got.util.PhonePreview
import com.onecode.jan.got.viewmodel.HouseOverviewViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun HousesOverviewScreen(
    onClick: (Int) -> Unit,
    viewModel: HouseOverviewViewModel = hiltViewModel()
) {
    val houseOverviewPagingItems = viewModel.getHouses().collectAsLazyPagingItems()
    Content(houseOverviewPagingItems = houseOverviewPagingItems, onClick = onClick)
}

@Composable
private fun Content(
    houseOverviewPagingItems: LazyPagingItems<UiHouseItem>,
    onClick: (Int) -> Unit
) {
    LazyColumn {
        items(
            items = houseOverviewPagingItems,
            key = { it.id }
        ) { house ->
            HouseCard(house = house!!, onClick = onClick)
        }

        when (val state = houseOverviewPagingItems.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                //TODO Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        modifier = Modifier.fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Refresh Loading"
                        )

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}
        }

        when (val state = houseOverviewPagingItems.loadState.append) { // Pagination
            is LoadState.Error -> {
                //TODO Pagination Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination Loading")

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}
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
        houseOverviewPagingItems = flowOf(getMockData()).collectAsLazyPagingItems(),
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

private fun getMockData() = PagingData.from(
    data = listOf(houseStark, houseAmbrose, houseAshwood)
)
//endregion