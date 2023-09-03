package com.mostafahelal.AtmoDrive.auth.data.model.modelRequest


import com.google.gson.annotations.SerializedName

data class SendCodeRequest(
    @SerializedName("mobile")
    val mobile: String
)