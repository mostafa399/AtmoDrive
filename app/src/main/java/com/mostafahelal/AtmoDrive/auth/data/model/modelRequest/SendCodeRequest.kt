package com.mostafahelal.AtmoDrive.auth.data.model.modelRequest


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.auth.domain.model.PhoneRequest

data class SendCodeRequest(
    @SerializedName("mobile")
    val mobile: String
)