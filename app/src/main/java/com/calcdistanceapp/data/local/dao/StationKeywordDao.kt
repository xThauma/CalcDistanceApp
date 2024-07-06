package com.calcdistanceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calcdistanceapp.data.local.DatabaseConstants.TABLE_STATION_KEYWORD
import com.calcdistanceapp.data.local.model.StationKeywordEntity

@Dao
interface StationKeywordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stationKeywordEntities: List<StationKeywordEntity>)

    @Query("SELECT * FROM $TABLE_STATION_KEYWORD")
    fun getAllStationKeywords(): List<StationKeywordEntity>

    @Query("SELECT * FROM $TABLE_STATION_KEYWORD WHERE stationId = :stationId")
    fun getStationKeywordsByStationId(stationId: Int): List<StationKeywordEntity>

    @Query("DELETE FROM $TABLE_STATION_KEYWORD")
    suspend fun deleteAllStationKeywords()
}