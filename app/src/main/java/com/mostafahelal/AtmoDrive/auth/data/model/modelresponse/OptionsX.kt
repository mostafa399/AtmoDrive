package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName

data class OptionsX(
    @SerializedName("device_types")
    val deviceTypes: List<String?>? = null,
    @SerializedName("gender")
    val gender: List<String?>? = null
)