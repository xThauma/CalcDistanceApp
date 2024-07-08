package com.calcdistanceapp.presentation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.calcdistanceapp.domain.model.Station


@Composable
fun SearchItemComposable(
    station: Station,
    onClick: (Station) -> Unit
) {
    val supportingContentText = StringBuilder().apply {
        append(station.city)
        if (station.city.isNotEmpty()) {
            append(", ")
        }
        append(station.region)
        if (station.region.isNotEmpty()) {
            append(", ")
        }
        append(station.country)
    }.toString()

    ListItem(
        headlineContent = { Text(text = station.name) },
        leadingContent = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
        supportingContent = { Text(text = supportingContentText) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier =
        Modifier
            .clickable {
                onClick(station)
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )
}
