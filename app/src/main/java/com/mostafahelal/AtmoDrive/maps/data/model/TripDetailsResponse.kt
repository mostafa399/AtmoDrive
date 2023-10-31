package com.mostafahelal.AtmoDrive.maps.data.model


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.TripDetails

data class TripDetailsResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("data")
    val tripData: TripData? = null
){
    fun asDomain():TripDetails{
        return TripDetails(message, status, tripData?.asDomain())
    }
}