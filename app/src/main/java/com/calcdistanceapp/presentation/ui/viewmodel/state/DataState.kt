package com.calcdistanceapp.presentation.ui.viewmodel.state

import com.calcdistanceapp.domain.model.Station

data class DataState(
    val searchResults: List<Station> = emptyList(),
    val startingStation: Station? = null,
    val endingStation: Station? = null,
    val isLoading: Boolean = true,
    val error: String = ""
)