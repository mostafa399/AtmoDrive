package com.mostafahelal.AtmoDrive.maps.data.model


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.CaptainDetails

data class CaptainDetailsResponse(
    @SerializedName("data")
    val data: CaptainData? = null,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
){
    fun asDomain():CaptainDetails{
        return CaptainDetails(message,status,data?.asDomain())
    }
}