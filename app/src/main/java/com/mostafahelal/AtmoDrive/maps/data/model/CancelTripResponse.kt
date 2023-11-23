package com.mostafahelal.AtmoDrive.maps.data.model


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelBeforeCaptainAccept
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelTrip

data class CancelTripResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
){
    fun asDomain():CancelTrip{
        return CancelTrip(message, status)
    }
}