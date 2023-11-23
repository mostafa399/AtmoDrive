package com.mostafahelal.AtmoDrive.Utils

import android.content.SharedPreferences
import javax.inject.Inject

class MySharedPreferences @Inject constructor(private val sharedPrefrences: SharedPreferences):ISharedPreferencesManager {
    companion object{
        const val KEY_IS_FIRST_TIME="is_first_time"
        const val USER_IS_LOGGED_IN = "USER_IS_LOGGED_IN"


    }
    override val editor: SharedPreferences.Editor
        get() = sharedPrefrences.edit()

    override fun updateIsFirstTime(isFirstTime: Boolean) {
        with(editor){
            putBoolean(KEY_IS_FIRST_TIME,isFirstTime)
            apply()
        }
    }

    override fun getIsFirstTime(): Boolean {
        return sharedPrefrences.getBoolean(KEY_IS_FIRST_TIME,true)
    }

    override fun userIsLoggedIn(): Boolean {
        val token = sharedPrefrences.getString(USER_IS_LOGGED_IN, null)
        return token != null
    }

    override fun getUserToken(): String {
        return sharedPrefrences.getString(USER_IS_LOGGED_IN, "").toString()
    }

    override fun saveUserAccessToken(token: String) {
        sharedPrefrences.edit().putString(USER_IS_LOGGED_IN, token).apply()
    }

    override fun deleteAccessToken(): Boolean {
        sharedPrefrences.edit().remove(USER_IS_LOGGED_IN).apply()
        return userIsLoggedIn()
    }

    override fun saveString(key: String, value: String?) {
        sharedPrefrences.edit()
            .putString(key, value)
            .apply()
    }



    override fun getString(key: String, defaultValue: String): String {
        return sharedPrefrences.getString(key, defaultValue) ?: defaultValue
    }

    override fun clearString(key: String) {
        sharedPrefrences.edit()
            .remove(key)
            .apply()
    }


}
//     fun saveRegisterResponse(response: UserData) {
//        editor.putString(Constants.TOKEN,response.rememberToken)
//        editor.putInt(Constants.STATUS, response.status!!)
//        editor.putInt(Constants.RATE, response.rate!!)
//        editor.putInt(Constants.ISDARKMODE, response.isDarkMode!!)
//        editor.putInt(Constants.SUSPEND, response.suspend!!)
//        editor.putString(Constants.AVATAR, response.avatar)
//        editor.putString(Constants.EMAIL, response.email)
//        editor.putString(Constants.FULLNAME, response.fullName)
//        editor.putString(Constants.LANG, response.lang)
//        editor.putString(Constants.PASSENGERCODE,response.passengerCode)
//        editor.putString(Constants.MOBILE,response.mobile)
//        editor.apply()
//    }
//     fun getRegisterResponse(): UserData {
//        return UserData(
//            avatar = preferences.getString(Constants.AVATAR,"").toString(),
//            email =preferences.getString(Constants.EMAIL,"").toString(),
//            fullName =preferences.getString(Constants.FULLNAME,"").toString(),
//            isDarkMode = preferences.getInt(Constants.ISDARKMODE,0),
//            lang = preferences.getString(Constants.LANG,"").toString(),
//            mobile = preferences.getString(Constants.MOBILE,"").toString(),
//            passengerCode = preferences.getString(Constants.PASSENGERCODE,"").toString(),
//            rate = preferences.getInt(Constants.RATE,0),
//            rememberToken = preferences.getString(Constants.TOKEN,"").toString(),
//            status = preferences.getInt(Constants.STATUS,0),
//            suspend = preferences.getInt(Constants.SUSPEND,0))
//    }
//
//}