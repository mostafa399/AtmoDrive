package com.mostafahelal.AtmoDrive.auth.domain.model

import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.OldUser

data class CodeResponse(
    val message: String,
    val status: Boolean,
    val is_new:Boolean,
    val data: UserData
)
