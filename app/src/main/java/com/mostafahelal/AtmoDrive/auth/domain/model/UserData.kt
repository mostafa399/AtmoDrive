package com.mostafahelal.AtmoDrive.auth.domain.model

data class UserData(
    val avatar: String?,
    val email: String?,
    val fullName: String?,
    val isDarkMode: Int?,
    val lang: String?,
    val mobile: String?,
    val passengerCode: String?,
    val rate: Int?,
    val rememberToken: String?,
    val status: Int?,
    val suspend: Int?
    )
