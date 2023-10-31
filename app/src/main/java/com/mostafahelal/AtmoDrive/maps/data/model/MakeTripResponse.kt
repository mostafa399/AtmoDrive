package com.mostafahelal.AtmoDrive.maps.data.model

import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTrip

data class MakeTripResponse(
    @SerializedName("data")
    val data: MakeTripData?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
){
    fun asDomain(): MakeTrip {
        return MakeTrip(data?.asDomain(),message,status)
    }
}
