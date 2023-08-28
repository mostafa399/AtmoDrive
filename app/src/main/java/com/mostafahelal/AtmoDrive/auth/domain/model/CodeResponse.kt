package com.mostafahelal.AtmoDrive.auth.domain.model

import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.DataX

data class CodeResponse(
    val isNew: Boolean?=false,
    val message: String,
    val status: Int,
    val data: DataX?=null
)
