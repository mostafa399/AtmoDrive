package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.auth.domain.model.UserData

data class User(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String?,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("is_dark_mode")
    val isDarkMode: Int,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("mobile")
    val mobile: String ,
    @SerializedName("options")
    val options: Options,
    @SerializedName("passenger_code")
    val passengerCode: String ,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("remember_token")
    val rememberToken: String,
    @SerializedName("shake_phone")
    val shakePhone: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("suspend")
    val suspend: Int
){
    fun asDomain():UserData{
        return UserData(avatar, email, fullName, isDarkMode, lang, mobile, passengerCode, rate, rememberToken, status, suspend)
    }
}