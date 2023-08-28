package com.mostafahelal.AtmoDrive.auth.domain.model

data class CodeRequest(
    val deviceToken:String,
    val mobile: String,
    val verificationCode: String
)
