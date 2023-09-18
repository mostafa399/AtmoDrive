package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.auth.domain.model.CodeResponse

data class CheckCodeResponse(
    @SerializedName("data")
    val data: OldUser ,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
){
    public fun asDomain():CodeResponse{
        return CodeResponse(message,status,data.isNew,data.isDomain())
    }
}