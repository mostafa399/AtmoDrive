package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.auth.domain.model.PhoneResponse

data class SendCodeResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("data")
    val data: NewUser
){
    public fun asDomain():PhoneResponse= PhoneResponse(message,status)
}
