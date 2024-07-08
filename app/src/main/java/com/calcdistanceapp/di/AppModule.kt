package com.calcdistanceapp.di

import com.calcdistanceapp.data.remote.converter.StationDtoToStationConverter
import com.calcdistanceapp.data.remote.converter.StationKeywordDtoToStationConverter
import com.calcdistanceapp.data.remote.model.StationDto
import com.calcdistanceapp.data.remote.model.StationKeywordDto
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindStationDtoToStationConverter(converter: StationDtoToStationConverter): Converter<StationDto, Station>

    @Binds
    abstract fun bindStationKeywordDtoToStationConverter(converter: StationKeywordDtoToStationConverter): Converter<StationKeywordDto, StationKeyword>
}