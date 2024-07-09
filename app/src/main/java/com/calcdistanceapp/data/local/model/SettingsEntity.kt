package com.calcdistanceapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.calcdistanceapp.data.local.DatabaseConstants.TABLE_SETTINGS
import java.time.LocalDate

@Entity(tableName = TABLE_SETTINGS)
data class SettingsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val creationDate: LocalDate = LocalDate.of(1990, 1,1),
)