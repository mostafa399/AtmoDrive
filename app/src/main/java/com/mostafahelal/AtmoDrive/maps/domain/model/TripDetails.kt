package com.mostafahelal.AtmoDrive.maps.domain.model

import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.data.model.TripData

data class TripDetails (
    val message: String,
    val status: Boolean,
    val tripData: DataTrip? = null
){
}