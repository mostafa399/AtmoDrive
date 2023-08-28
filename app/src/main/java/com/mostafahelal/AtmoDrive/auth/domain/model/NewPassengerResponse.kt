package com.mostafahelal.AtmoDrive.auth.domain.model

import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.DataX

data class NewPassengerResponse(
    val status: Int,
    val message: String,
    val data: DataX
)
