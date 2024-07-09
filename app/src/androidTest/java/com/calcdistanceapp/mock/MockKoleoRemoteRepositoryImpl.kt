package com.calcdistanceapp.mock

import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import com.calcdistanceapp.domain.repository.KoleoRemoteRepository
import javax.inject.Inject

class MockKoleoRemoteRepositoryImpl @Inject constructor(): KoleoRemoteRepository {
    override suspend fun getStations(): List<Station> {
        val station1 = Station(
            id = 1,
            name = "Central Station",
            latitude = 40.712776,
            longitude = -74.005974,
            hits = 1500,
            city = "New York",
            region = "New York",
            country = "USA",
            isGroup = false
        )

        val station2 = Station(
            id = 2,
            name = "King's Cross",
            latitude = 51.531270,
            longitude = -0.125430,
            hits = 2000,
            city = "London",
            region = "Greater London",
            country = "UK",
            isGroup = false
        )

        val station3 = Station(
            id = 3,
            name = "Gare du Nord",
            latitude = 48.880948,
            longitude = 2.355327,
            hits = 1800,
            city = "Paris",
            region = "Ile-de-France",
            country = "France",
            isGroup = false
        )

        val station4 = Station(
            id = 4,
            name = "Warszawa",
            latitude = 0.0,
            longitude = 2.355327,
            hits = 1800,
            city = "Paris",
            region = "Ile-de-France",
            country = "France",
            isGroup = false
        )


        return listOf(station1, station2, station3, station4)
    }

    override suspend fun getStationKeywords(): List<StationKeyword> {
        val keyword1 = StationKeyword(
            keyword = "Central Station",
            stationId = 1
        )

        val keyword2 = StationKeyword(
            keyword = "King's Cross",
            stationId = 2
        )

        val keyword3 = StationKeyword(
            keyword = "Gare du Nord",
            stationId = 3
        )

        val keyword4 = StationKeyword(
            keyword = "Warszawa",
            stationId = 4
        )

        return listOf(keyword1, keyword2, keyword3, keyword4)
    }
}