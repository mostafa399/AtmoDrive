package com.mostafahelal.AtmoDrive.auth.domain.model

import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.User

data class CheckData(
    val isNew: Boolean,
    val user: UserData?

    )
