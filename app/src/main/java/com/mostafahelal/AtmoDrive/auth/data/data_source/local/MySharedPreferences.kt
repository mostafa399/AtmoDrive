package com.mostafahelal.AtmoDrive.auth.data.data_source.local

import android.content.SharedPreferences
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.auth.domain.model.UserData
import javax.inject.Inject

class MySharedPreferences @Inject constructor(private val preferences: SharedPreferences) {
    val editor: SharedPreferences.Editor
        get() = preferences.edit()

    fun isFirstAppLaunch(): Boolean {
        return preferences.getBoolean(Constants.IS_FIRST_APP_LAUNCH, true)
    }

    fun saveFirstAppLaunch(value: Boolean) {
        preferences.edit().putBoolean(Constants.IS_FIRST_APP_LAUNCH, value).apply()
    }

    fun userIsLoggedIn() : Boolean {
        val token = preferences.getString(Constants.USER_IS_LOGGED_IN, null)
        return token != null
    }

    fun getUserToken(): String {
        return preferences.getString(Constants.USER_IS_LOGGED_IN, "").toString()
    }

    fun saveUserAccessToken(token: String) {
        preferences.edit().putString(Constants.USER_IS_LOGGED_IN, token).apply()
    }

    fun deleteAccessToken(): Boolean {
        preferences.edit().remove(Constants.USER_IS_LOGGED_IN).apply()
        return userIsLoggedIn()
    }
     fun saveRegisterResponse(response: UserData) {
        editor.putString(Constants.TOKEN,response.rememberToken)
        editor.putInt(Constants.STATUS, response.status!!)
        editor.putInt(Constants.RATE, response.rate!!)
        editor.putInt(Constants.ISDARKMODE, response.isDarkMode!!)
        editor.putInt(Constants.SUSPEND, response.suspend!!)
        editor.putString(Constants.AVATAR, response.avatar)
        editor.putString(Constants.EMAIL, response.email)
        editor.putString(Constants.FULLNAME, response.fullName)
        editor.putString(Constants.LANG, response.lang)
        editor.putString(Constants.PASSENGERCODE,response.passengerCode)
        editor.putString(Constants.MOBILE,response.mobile)
        editor.apply()
    }
     fun getRegisterResponse(): UserData {
        return UserData(
            avatar = preferences.getString(Constants.AVATAR,"").toString(),
            email =preferences.getString(Constants.EMAIL,"").toString(),
            fullName =preferences.getString(Constants.FULLNAME,"").toString(),
            isDarkMode = preferences.getInt(Constants.ISDARKMODE,0),
            lang = preferences.getString(Constants.LANG,"").toString(),
            mobile = preferences.getString(Constants.MOBILE,"").toString(),
            passengerCode = preferences.getString(Constants.PASSENGERCODE,"").toString(),
            rate = preferences.getInt(Constants.RATE,0),
            rememberToken = preferences.getString(Constants.TOKEN,"").toString(),
            status = preferences.getInt(Constants.STATUS,0),
            suspend = preferences.getInt(Constants.SUSPEND,0))
    }

}