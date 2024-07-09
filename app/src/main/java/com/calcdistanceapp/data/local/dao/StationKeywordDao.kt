package com.calcdistanceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calcdistanceapp.data.local.DatabaseConstants.TABLE_STATION_KEYWORD
import com.calcdistanceapp.data.local.model.StationKeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationKeywordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stationKeywordEntities: List<StationKeywordEntity>)

    @Query("SELECT * FROM $TABLE_STATION_KEYWORD")
    fun getAllStationKeywords(): List<StationKeywordEntity>

    @Query("DELETE FROM $TABLE_STATION_KEYWORD")
    suspend fun deleteAllStationKeywords()

    @Query("SELECT * FROM $TABLE_STATION_KEYWORD WHERE LOWER(keyword) LIKE ('%' || LOWER(:keyword) || '%') LIMIT 50")
    fun searchKeywords(keyword: String): Flow<List<StationKeywordEntity>>
}