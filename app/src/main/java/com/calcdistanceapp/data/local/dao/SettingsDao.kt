package com.calcdistanceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calcdistanceapp.data.local.DatabaseConstants.TABLE_SETTINGS
import com.calcdistanceapp.data.local.model.SettingsEntity

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settingsEntity: SettingsEntity)

    @Query("SELECT * FROM $TABLE_SETTINGS")
    fun getSettings(): SettingsEntity?

    @Query("DELETE FROM $TABLE_SETTINGS")
    suspend fun deleteAllSettings()
}