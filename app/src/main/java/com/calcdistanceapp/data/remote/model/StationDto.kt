package com.calcdistanceapp.data.remote.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class StationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("name_slug") val nameSlug: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("hits") val hits: Int,
    @SerializedName("ibnr") val ibnr: Int,
    @SerializedName("city") val city: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("localised_name") val localizedName: String?,
    @SerializedName("is_group") val isGroup: Boolean,
    @SerializedName("has_announcements") val hasAnnouncements: Boolean,
    @SerializedName("is_nearby_station_enabled") val isNearbyStationEnabled: Boolean
)