package com.mostafahelal.AtmoDrive.auth.data.model.modelRequest


import com.google.gson.annotations.SerializedName

data class RegisterPassengerRequest(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("device_id")
    val deviceId: String?,
    @SerializedName("device_token")
    val deviceToken: String?,
    @SerializedName("device_type")
    val deviceType: String?,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("email")
    val email: String?

)
