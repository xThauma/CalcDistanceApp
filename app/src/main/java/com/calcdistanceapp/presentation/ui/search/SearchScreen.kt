package com.calcdistanceapp.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calcdistanceapp.data.event.KoleoEvent
import com.calcdistanceapp.presentation.ui.ResultScreenComposable
import com.calcdistanceapp.presentation.ui.viewmodel.state.DataState

@Composable
fun SearchScreenComposable(
    dataState: DataState,
    resultText: String,
    onEvent: (KoleoEvent) -> Unit
) {
    var expandedSearchBar by rememberSaveable { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
                text = "Choose 2 stations to calculate distance between them",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
            SearchBarComposable(
                hintText = "Starting station",
                searchText = dataState.startingStation?.name ?: "",
                expanded = expandedSearchBar == 0,
                searchResults = dataState.searchResults,
                onExpandedChange = { isExpanded ->
                    expandedSearchBar = if (isExpanded) 0 else null
                },
                onSearch = { searchText -> onEvent(KoleoEvent.SearchStationByKeyword(searchText)) },
                onSearchFinish = { station -> onEvent(KoleoEvent.UpdateStation.Starting(station)) }
            )
            SearchBarComposable(
                hintText = "Ending station",
                searchText = dataState.endingStation?.name ?: "",
                expanded = expandedSearchBar == 1,
                searchResults = dataState.searchResults,
                onExpandedChange = { isExpanded ->
                    expandedSearchBar = if (isExpanded) 1 else null
                },
                onSearch = { searchText -> onEvent(KoleoEvent.SearchStationByKeyword(searchText)) },
                onSearchFinish = { station -> onEvent(KoleoEvent.UpdateStation.Ending(station)) }
            )
        }
        if (expandedSearchBar == null && resultText.isNotBlank()) {
            ResultScreenComposable(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                resultText = resultText
            )
        }
    }
}