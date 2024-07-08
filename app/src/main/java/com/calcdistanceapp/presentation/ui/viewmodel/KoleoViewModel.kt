package com.calcdistanceapp.presentation.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calcdistanceapp.data.DataResult
import com.calcdistanceapp.data.event.KoleoEvent
import com.calcdistanceapp.di.IoDispatcher
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.usecase.GetStationKeywordsUseCase
import com.calcdistanceapp.domain.usecase.GetStationsUseCase
import com.calcdistanceapp.domain.usecase.InsertCreationDateUseCase
import com.calcdistanceapp.domain.usecase.SearchStationsByKeywordUseCase
import com.calcdistanceapp.presentation.ui.viewmodel.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@HiltViewModel
class KoleoViewModel @Inject constructor(
    private val getStationsUseCase: GetStationsUseCase,
    private val getStationKeywordsUseCase: GetStationKeywordsUseCase,
    private val insertCreationDateUseCase: InsertCreationDateUseCase,
    private val searchStationsByKeywordUseCase: SearchStationsByKeywordUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _dataState = MutableStateFlow(DataState())
    val dataState: StateFlow<DataState> = _dataState

    val resultText: String
        get() {
            val start = _dataState.value.startingStation
            val end = _dataState.value.endingStation

            if (start == null || end == null) {
                return ""
            }

            return if (start.id == end.id) {
                "You can't calculate distance between 2 same stations"
            } else {
                "Total distance: ${calculateDistanceBetweenStations(start, end)} km"
            }
        }

    init {
        fetchStationsAndKeywords()
    }

    fun onEvent(event: KoleoEvent) {
        when (event) {
            is KoleoEvent.FetchEvent -> fetchStationsAndKeywords()
            is KoleoEvent.SearchStationByKeyword -> searchStationsByKeyword(keyword = event.keyword)
            is KoleoEvent.UpdateStation.Ending -> updateStartingStation(station = event.station)
            is KoleoEvent.UpdateStation.Starting -> updateEndingStation(station = event.station)
        }
    }

    private fun fetchStationsAndKeywords() {
        viewModelScope.launch(ioDispatcher) {
            _dataState.update {
                it.copy(
                    isLoading = true,
                    error = ""
                )
            }

            try {
                val stationsResult = async { getStationsUseCase.invoke() }
                val keywordsResult = async { getStationKeywordsUseCase.invoke() }

                val stationsDataResult = stationsResult.await()
                val keywordsDataResult = keywordsResult.await()
                _dataState.update {
                    it.copy(isLoading = false)
                }

                if (stationsDataResult.isFetchRemote() or keywordsDataResult.isFetchRemote()) {
                    insertCreationDateUseCase.invoke()
                }

                if (stationsDataResult.isError() || keywordsDataResult.isError()) {
                    _dataState.update {
                        it.copy(error = (stationsDataResult as DataResult.Error).msg)
                    }
                }
                searchStationsByKeyword("")
            } catch (e: Exception) {
                _dataState.update {
                    it.copy(error = "Error fetching data, please try again later")
                }
            }
        }
    }

    private fun searchStationsByKeyword(keyword: String) {
        viewModelScope.launch(ioDispatcher) {
            searchStationsByKeywordUseCase(keyword)
                .collect { searchResults ->
                    _dataState.update { it.copy(searchResults = searchResults) }
                }
        }
    }

    private fun updateStartingStation(station: Station) {
        _dataState.update {
            it.copy(
                startingStation = station
            )
        }
    }

    private fun updateEndingStation(station: Station) {
        _dataState.update {
            it.copy(
                endingStation = station
            )
        }
    }

    @VisibleForTesting
    fun calculateDistanceBetweenStations(station1: Station, station2: Station): Int {
        val lat1 = Math.toRadians(station1.latitude)
        val lon1 = Math.toRadians(station1.longitude)
        val lat2 = Math.toRadians(station2.latitude)
        val lon2 = Math.toRadians(station2.longitude)

        val deltaLat = lat2 - lat1
        val deltaLon = lon2 - lon1

        val a = sin(deltaLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(deltaLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val radiusOfEarth = 6371.0
        val distanceInKm = radiusOfEarth * c
        return (distanceInKm * 0.621371).toInt()
    }
}