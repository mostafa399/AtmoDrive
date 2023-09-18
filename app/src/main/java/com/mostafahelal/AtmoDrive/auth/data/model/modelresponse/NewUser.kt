package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse

import com.google.gson.annotations.SerializedName

data class NewUser(
    @SerializedName("is_new")
    val is_new: Boolean,
    @SerializedName("full_name")
    val full_name: String?,
)
