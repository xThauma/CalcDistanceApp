package com.calcdistanceapp.di

import com.calcdistanceapp.data.converter.StationDtoToStationConverter
import com.calcdistanceapp.data.converter.StationKeywordDtoToStationConverter
import com.calcdistanceapp.data.model.StationDto
import com.calcdistanceapp.data.model.StationKeywordDto
import com.calcdistanceapp.data.repository.KoleoRepositoryImpl
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import com.calcdistanceapp.domain.repository.KoleoRepository
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

    @Binds
    abstract fun bindKoleoRepository(repository: KoleoRepositoryImpl): KoleoRepository
}