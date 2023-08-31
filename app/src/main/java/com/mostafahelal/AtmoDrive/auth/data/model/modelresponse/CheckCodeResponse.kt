package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName

data class CheckCodeResponse(
    @SerializedName("data")
    val data: Data? ,
    @SerializedName("is_new")
    val isNew: Boolean?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)