package com.onecode.jan.got.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.onecode.jan.got.R
import com.onecode.jan.got.designsystem.theme.IceAndFireTheme
import com.onecode.jan.got.designsystem.theme.Typography
import com.onecode.jan.got.util.PhonePreview

@Composable
fun HouseDetailScreen(
    id: Int?,
    viewModel: HouseDetailViewModel = hiltViewModel()
) {
    id?.let {
        val state = viewModel.uiStateFlow.collectAsState(HouseDetailUiState.Loading)
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
        Text(stringResource(R.string.house_details_loading))
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
        TitleContent(title = stringResource(id = R.string.house_name), content = data.name)
        data.region?.let {
            TitleContent(
                title = stringResource(id = R.string.house_region),
                content = it
            )
        }
        data.coatOfArms?.let {
            TitleContent(
                title = stringResource(id = R.string.house_coat_of_arms),
                content = it
            )
        }
        data.words?.let {
            TitleContent(
                title = stringResource(id = R.string.house_words),
                content = it
            )
        }
        data.titles?.let {
            TitleContent(
                title = stringResource(id = R.string.house_titles),
                content = it
            )
        }
        data.seats?.let {
            TitleContent(
                title = stringResource(id = R.string.house_seats),
                content = it
            )
        }
        data.founded?.let {
            TitleContent(
                title = stringResource(id = R.string.house_founded),
                content = it
            )
        }
        data.diedOut?.let {
            TitleContent(
                title = stringResource(id = R.string.house_died_out),
                content = it
            )
        }
        data.ancestralWeapons?.let {
            TitleContent(
                title = stringResource(id = R.string.house_ancestral_weapons),
                content = it
            )
        }
    }
}

@Composable
private fun TitleContent(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 4.dp),
            text = title,
            style = Typography.titleMedium
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(size = 2.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = content,
                style = Typography.bodySmall
            )
        }
    }
}

@Composable
private fun TitleContent(title: String, content: List<String>) {
    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 4.dp),
            text = title,
            style = Typography.titleMedium
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(size = 2.dp)
        ) {
            content.forEach {
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = it,
                    style = Typography.bodySmall
                )
            }
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
)

private val houseAlgood = UiHouseDetail(
    name = "House Algood",
    region = "The Westerlands",
    coatOfArms = "A golden wreath, on a blue field with a gold border(Azure, a garland of laurel within a bordure or)",
)
//endregion