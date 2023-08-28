package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("avatar")
    val avatar: String? = "",
    @SerializedName("email")
    val email: Any? = Any(),
    @SerializedName("full_name")
    val fullName: String? = "",
    @SerializedName("is_dark_mode")
    val isDarkMode: Int? = 0,
    @SerializedName("lang")
    val lang: String? = "",
    @SerializedName("mobile")
    val mobile: String? = "",
    @SerializedName("options")
    val options: OptionsX? = OptionsX(),
    @SerializedName("passenger_code")
    val passengerCode: String? = "",
    @SerializedName("rate")
    val rate: Int? = 0,
    @SerializedName("remember_token")
    val rememberToken: String? = "",
    @SerializedName("shake_phone")
    val shakePhone: Int? = 0,
    @SerializedName("status")
    val status: Int? = 0,
    @SerializedName("suspend")
    val suspend: Int? = 0
)