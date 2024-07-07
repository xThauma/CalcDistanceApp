package com.calcdistanceapp.presentation.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.calcdistanceapp.domain.model.Station

@Composable
fun SearchItemScreenComposable(
    searchResults: List<Station>,
    onClick: (Station) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()
    ) {
        items(searchResults) { station ->
            SearchItemComposable(station = station, onClick = onClick)
        }
    }
}