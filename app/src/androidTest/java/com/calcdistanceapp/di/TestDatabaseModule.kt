package com.calcdistanceapp.di

import android.content.Context
import androidx.room.Room
import com.calcdistanceapp.data.local.KoleoDatabase
import com.calcdistanceapp.data.local.converter.EntityConverters
import com.calcdistanceapp.data.local.converter.SettingsEntityToSettingsConverter
import com.calcdistanceapp.data.local.converter.StationEntityToStationConverter
import com.calcdistanceapp.data.local.converter.StationKeywordEntityToStationKeywordConverter
import com.calcdistanceapp.data.local.converter.StationKeywordToStationKeywordEntityConverter
import com.calcdistanceapp.data.local.converter.StationToStationEntityConverter
import com.calcdistanceapp.data.local.converter.StringPolishAccentToStringNoAccentConverter
import com.calcdistanceapp.data.local.dao.SettingsDao
import com.calcdistanceapp.data.local.dao.StationDao
import com.calcdistanceapp.data.local.dao.StationKeywordDao
import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.data.local.model.StationEntity
import com.calcdistanceapp.data.local.model.StationKeywordEntity
import com.calcdistanceapp.data.local.repository.KoleoLocalRepository
import com.calcdistanceapp.data.local.repository.KoleoLocalRepositoryImpl
import com.calcdistanceapp.data.remote.converter.StationDtoToStationConverter
import com.calcdistanceapp.data.remote.converter.StationKeywordDtoToStationConverter
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.Settings
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TestDatabaseModule {

    @Binds
    abstract fun bindStationEntityToStationConverter(converter: StationEntityToStationConverter): Converter<StationEntity, Station>

    @Binds
    abstract fun bindStationKeywordEntityToStationConverter(converter: StationKeywordEntityToStationKeywordConverter): Converter<StationKeywordEntity, StationKeyword>

    @Binds
    abstract fun bindSettingsEntityToSettingsConverter(converter: SettingsEntityToSettingsConverter): Converter<SettingsEntity, Settings>

    @Binds
    abstract fun bindStationToStationEntityConverter(converter: StationToStationEntityConverter): Converter<Station, StationEntity>

    @Binds
    abstract fun bindStationKeywordToStationEntityConverter(converter: StationKeywordToStationKeywordEntityConverter): Converter<StationKeyword, StationKeywordEntity>

    @Binds
    abstract fun bindStringPolishAccentToStringNoAccentConverter(converter: StringPolishAccentToStringNoAccentConverter): Converter<String, String>

    @Binds
    abstract fun bindKoleoLocalRepository(repository: KoleoLocalRepositoryImpl): KoleoLocalRepository

    companion object {

        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): KoleoDatabase {
            return Room.inMemoryDatabaseBuilder(
                context,
                KoleoDatabase::class.java,
            ).build()
        }

        @Provides
        fun provideStationDao(database: KoleoDatabase): StationDao {
            return database.stationDao()
        }

        @Provides
        fun provideStationKeywordDao(database: KoleoDatabase): StationKeywordDao {
            return database.stationKeywordDao()
        }

        @Provides
        fun provideSettingsDao(database: KoleoDatabase): SettingsDao {
            return database.settingsDao()
        }

        @Provides
        fun provideEntityConverters(
            stationEntityToStationConverter: StationEntityToStationConverter,
            stationKeywordEntityToStationKeywordConverter: StationKeywordEntityToStationKeywordConverter,
            stationToEntityConverter: StationToStationEntityConverter,
            stationKeywordToEntityConverter: StationKeywordToStationKeywordEntityConverter,
            stringPolishAccentToStringNoAccentConverter: StringPolishAccentToStringNoAccentConverter,
            stationDtoToStationConverter: StationDtoToStationConverter,
            stationKeywordDtoToStationConverter: StationKeywordDtoToStationConverter
        ): EntityConverters {
            return EntityConverters(
                stationEntityToStationConverter,
                stationKeywordEntityToStationKeywordConverter,
                stationToEntityConverter,
                stationKeywordToEntityConverter,
                stringPolishAccentToStringNoAccentConverter,
                stationDtoToStationConverter,
                stationKeywordDtoToStationConverter
            )
        }

        @Provides
        fun provideGson(): Gson {
            return Gson()
        }
    }
}