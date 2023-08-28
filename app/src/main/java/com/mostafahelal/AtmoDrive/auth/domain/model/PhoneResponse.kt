package com.mostafahelal.AtmoDrive.auth.domain.model

import com.google.gson.annotations.SerializedName

data class PhoneResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: Int? = null
)
