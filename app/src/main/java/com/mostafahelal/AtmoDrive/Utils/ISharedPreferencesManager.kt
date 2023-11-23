package com.mostafahelal.AtmoDrive.Utils

import android.content.SharedPreferences

interface ISharedPreferencesManager {
    val editor : SharedPreferences.Editor
    fun updateIsFirstTime(isFirstTime:Boolean)
    fun getIsFirstTime():Boolean
    fun  userIsLoggedIn() : Boolean
    fun getUserToken(): String
    fun saveUserAccessToken(token: String)
    fun deleteAccessToken(): Boolean
    fun saveString(key: String, value: String?)
    fun getString(key: String, defaultValue: String = ""): String
    fun clearString(key: String)
}