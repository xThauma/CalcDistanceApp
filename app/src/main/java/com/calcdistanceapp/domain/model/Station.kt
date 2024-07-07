package com.calcdistanceapp.domain.model

data class Station(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val hits: Int,
    val city: String,
    val region: String,
    val country: String,
    val isGroup: Boolean,
)