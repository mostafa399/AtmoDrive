package com.mostafahelal.AtmoDrive.auth.domain.model

data class NewPassengerRequest(
    val deviceToken: String?,
    val fullName: String?,
    val mobile: String?,
    val email:String?
)
