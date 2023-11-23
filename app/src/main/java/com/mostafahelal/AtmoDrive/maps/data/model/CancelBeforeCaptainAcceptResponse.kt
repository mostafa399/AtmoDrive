package com.mostafahelal.AtmoDrive.maps.data.model


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelBeforeCaptainAccept

data class CancelBeforeCaptainAcceptResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
){
    fun asDomain():CancelBeforeCaptainAccept{
        return CancelBeforeCaptainAccept(message, status)
    }
}