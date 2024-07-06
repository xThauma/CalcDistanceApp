package com.calcdistanceapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.calcdistanceapp.data.local.DatabaseConstants.TABLE_STATION_KEYWORD

@Entity(tableName = TABLE_STATION_KEYWORD)
data class StationKeywordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val keyword: String,
    val stationId: Int,
)