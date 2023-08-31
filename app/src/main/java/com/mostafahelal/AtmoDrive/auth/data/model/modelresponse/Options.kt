package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName

data class Options(
    @SerializedName("device_types")
    val deviceTypes: List<String?>?,
    @SerializedName("gender")
    val gender: List<String?>?
)