package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse

import com.google.gson.annotations.SerializedName

data class RegisterPassengerResponse(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int
)
