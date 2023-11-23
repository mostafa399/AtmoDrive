package com.mostafahelal.AtmoDrive.maps.data.model


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.ConfirmTrip

data class ConfirmTripResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("trip_id")
    val tripId: Int
){
    fun asDomain():ConfirmTrip{
       return ConfirmTrip(message,status, tripId)
    }
}