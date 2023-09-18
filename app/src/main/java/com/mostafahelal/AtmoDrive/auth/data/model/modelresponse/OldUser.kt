package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.auth.domain.model.UserData

data class OldUser(
    @SerializedName("is_new")
    val isNew: Boolean,
    @SerializedName("user")
    val user: User?
){

   public fun isDomain():UserData=UserData( user?.avatar, user?.email, user?.fullName,
      user?. isDarkMode,
       user?.lang,
      user?. mobile,
      user?. passengerCode,
       user?.rate,
      user?. rememberToken,
      user?. status,
      user?. suspend,)
}