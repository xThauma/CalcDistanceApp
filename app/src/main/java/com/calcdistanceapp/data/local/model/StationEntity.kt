package com.calcdistanceapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.calcdistanceapp.data.local.DatabaseConstants.TABLE_STATION

@Entity(tableName = TABLE_STATION)
data class StationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val stationId: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val hits: Int,
)