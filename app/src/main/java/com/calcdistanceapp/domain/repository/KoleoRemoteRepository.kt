package com.calcdistanceapp.domain.repository

import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword

interface KoleoRemoteRepository {
    suspend fun getStations(): List<Station>
    suspend fun getStationKeywords(): List<StationKeyword>
}