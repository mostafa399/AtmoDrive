package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName

data class CheckCodeResponse(
    @SerializedName("data")
    val data: DataX? = DataX(),
    @SerializedName("is_new")
    val isNew: Boolean? = false,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: Int? = 0
)