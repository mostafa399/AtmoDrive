package com.mostafahelal.AtmoDrive.maps.domain.model

data class ConfirmTrip(
    val message: String,
    val status: Boolean,
    val tripId: Int? = null
)
