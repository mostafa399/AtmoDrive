package com.mostafahelal.AtmoDrive.auth.domain.model

import com.google.gson.annotations.SerializedName

data class NewPassengerRequest(
    val avatar: String,
    val deviceId: String,
    val deviceToken: String,
    val deviceType: String,
    val fullName: String,
    val mobile: String
)
