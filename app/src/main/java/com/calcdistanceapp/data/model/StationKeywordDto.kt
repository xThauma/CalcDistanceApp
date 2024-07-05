package com.calcdistanceapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class StationKeywordDto(
    @SerializedName("id") val id: Int,
    @SerializedName("keyword") val keyword: String,
    @SerializedName("station_id") val stationId: Int
)