package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.data.local.model.StationEntity
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.Settings
import com.calcdistanceapp.domain.model.Station
import javax.inject.Inject

class SettingsEntityToSettingsConverter @Inject constructor() :
    Converter<SettingsEntity, Settings> {
    override fun convert(from: SettingsEntity): Settings =
        Settings(creationDate = from.creationDate)
}
