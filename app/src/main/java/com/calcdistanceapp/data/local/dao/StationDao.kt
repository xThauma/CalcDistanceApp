package com.calcdistanceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calcdistanceapp.data.local.DatabaseConstants.TABLE_STATION
import com.calcdistanceapp.data.local.model.StationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stationEntities: List<StationEntity>)

    @Query("SELECT * FROM $TABLE_STATION")
    fun getAllStations(): List<StationEntity>

    @Query("SELECT * FROM $TABLE_STATION WHERE stationId = :stationId")
    fun getStationByStationId(stationId: Int): StationEntity

    @Query("DELETE FROM $TABLE_STATION")
    suspend fun deleteAllStations()

    @Query("SELECT * FROM $TABLE_STATION WHERE stationId IN (:stationIds) and isGroup = 0 ORDER BY hits DESC LIMIT 50")
    fun getStationsByIds(stationIds: List<Int>): Flow<List<StationEntity>>
}