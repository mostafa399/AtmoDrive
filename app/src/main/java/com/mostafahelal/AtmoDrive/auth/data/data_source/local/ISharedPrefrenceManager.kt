package com.mostafahelal.AtmoDrive.auth.data.data_source.local

import android.content.SharedPreferences
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse

interface ISharedPrefrenceManager {
    val editor :SharedPreferences.Editor
    fun updateIsFirstTime(isFirstTime:Boolean)
    fun getIsFirstTime():Boolean
    fun  userIsLoggedIn() : Boolean
    fun getUserToken(): String
    fun saveUserAccessToken(token: String)
    fun saveRegisterResponse(response: RegisterPassengerResponse?)
    fun getRegisterResponse(): RegisterPassengerResponse?
    fun deleteAccessToken(): Boolean
}