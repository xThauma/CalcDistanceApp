package com.calcdistanceapp.domain.model

data class Station(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val hits: Int,
)