package com.onecode.jan.got.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.onecode.jan.got.designsystem.theme.IceAndFireTheme
import com.onecode.jan.got.designsystem.theme.Typography
import com.onecode.jan.got.util.PhonePreview

@Composable
fun HouseDetailScreen(
    id: Int?,
    viewModel: HouseDetailViewModel = hiltViewModel()
) {
    id?.let {
        val state = viewModel.uiStateFlow.collectAsState()
        viewModel.fetchHouseById(it)
        Content(state = state.value)
    } ?: Error()
}

@Composable
private fun Content(
    state: HouseDetailUiState,
) {
    Column {
        when (state) {
            HouseDetailUiState.Loading -> Loading()
            is HouseDetailUiState.Success -> HouseDetail(state.data)
            is HouseDetailUiState.Error -> Error()
        }
    }
}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("Das Haus wird zu den Banner gerufen!")
    }
}

@Composable
private fun Error() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Das Haus wird zu den Banner gerufen!")
    }
}

@Composable
private fun HouseDetail(data: UiHouseDetail) {
    Column {
        TitleContent(title = "Name", content = data.name)
        TitleContent(title = "Region", content = data.region)
        TitleContent(title = "Coat of arms", content = data.coatOfArms)
        TitleContent(title = "Words", content = data.words)
        TitleContent(title = "Titles", content = data.titles)
        TitleContent(title = "Seats", content = data.seats)
        TitleContent(title = "Founded", content = data.founded)
        TitleContent(title = "Died out", content = data.diedOut)
        TitleContent(title = "Ancestral weapons", content = data.ancestralWeapons)
    }
}

@Composable
private fun TitleContent(title: String, content: String) {
    if (content.isNotEmpty()) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp),
                text = title,
                style = Typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                text = content,
                style = Typography.bodySmall
            )
            Divider(modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
private fun TitleContent(title: String, content: List<String>) {
    if (content.isNotEmpty() && content.first().isNotEmpty()) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp),
                text = title,
                style = Typography.titleMedium
            )
            content.forEach {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    text = it,
                    style = Typography.bodySmall
                )
            }
            Divider(modifier = Modifier.padding(top = 4.dp))
        }
    }
}

//region Preview
@Composable
@PhonePreview
private fun PreviewFull() {
    IceAndFireTheme {
        HouseDetail(data = houseArryn)
    }
}

@Composable
@PhonePreview
private fun PreviewPartial() {
    IceAndFireTheme {
        HouseDetail(data = houseAlgood)
    }
}

private val houseArryn = UiHouseDetail(
    name = "House Arryn of the Eyrie",
    region = "The Vale",
    coatOfArms = "A sky-blue falcon soaring against a white moon, on a sky-blue field(Bleu celeste, upon a plate a falcon volant of the field)",
    words = "As High as Honor",
    titles = listOf(
        "King of Mountain and Vale (formerly)",
        "Lord of the Eyrie",
        "Defender of the Vale",
        "Warden of the East"
    ),
    seats = listOf(
        "The Eyrie (summer)",
        "Gates of the Moon (winter)"
    ),
    founded = "Coming of the Andals",
    diedOut = "Never",
    ancestralWeapons = listOf()
)

private val houseAlgood = UiHouseDetail(
    name = "House Algood",
    region = "The Westerlands",
    coatOfArms = "A golden wreath, on a blue field with a gold border(Azure, a garland of laurel within a bordure or)",
)
//endregion