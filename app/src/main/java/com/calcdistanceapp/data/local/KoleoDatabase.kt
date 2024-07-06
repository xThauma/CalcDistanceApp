package com.calcdistanceapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.calcdistanceapp.data.local.DatabaseConstants.DATABASE_KOLEO
import com.calcdistanceapp.data.local.converter.DateConverter
import com.calcdistanceapp.data.local.dao.SettingsDao
import com.calcdistanceapp.data.local.dao.StationDao
import com.calcdistanceapp.data.local.dao.StationKeywordDao
import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.data.local.model.StationEntity
import com.calcdistanceapp.data.local.model.StationKeywordEntity

@Database(
    entities = [SettingsEntity::class, StationEntity::class, StationKeywordEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class KoleoDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao
    abstract fun stationDao(): StationDao
    abstract fun stationKeywordDao(): StationKeywordDao

    companion object {

        @Volatile
        private var INSTANCE: KoleoDatabase? = null

        fun getDatabase(context: Context): KoleoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KoleoDatabase::class.java,
                    DATABASE_KOLEO
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}