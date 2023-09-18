package com.mostafahelal.AtmoDrive.auth.data.model.modelRequest


import com.google.gson.annotations.SerializedName

data class CheckCodeRequest(
    @SerializedName("device_token")
    val deviceToken: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("verification_code")
    val verificationCode: String
)
//{
//    fun asDomain():CodeRequest{
//        return CodeRequest(mobile,verificationCode,deviceToken)
//    }
//}