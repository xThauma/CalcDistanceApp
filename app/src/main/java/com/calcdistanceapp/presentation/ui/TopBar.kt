package com.calcdistanceapp.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComposable() {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "Calculate distance",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                )
                Text(
                    text = "Choose 2 stations to calculate distance between them",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}