package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.auth.domain.model.CheckData
import com.mostafahelal.AtmoDrive.auth.domain.model.UserData

data class OldUser(
    @SerializedName("is_new")
    val isNew: Boolean,
    @SerializedName("user")
    val user: User?
){

   public fun isDomain():CheckData=CheckData( isNew,user?.asDomain())
}