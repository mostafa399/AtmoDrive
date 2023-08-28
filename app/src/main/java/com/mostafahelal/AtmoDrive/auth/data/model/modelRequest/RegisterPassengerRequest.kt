package com.mostafahelal.AtmoDrive.auth.data.model.modelRequest


import com.google.gson.annotations.SerializedName

data class RegisterPassengerRequest(
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("device_id")
    val deviceId: String? = null,
    @SerializedName("device_token")
    val deviceToken: String? = null,
    @SerializedName("device_type")
    val deviceType: String? = null,
    @SerializedName("full_name")
    val fullName: String? = null,
    @SerializedName("mobile")
    val mobile: String? = null
)