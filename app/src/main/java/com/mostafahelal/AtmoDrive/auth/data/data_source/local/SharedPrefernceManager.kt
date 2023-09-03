package com.mostafahelal.AtmoDrive.auth.data.data_source.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Constants
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import javax.inject.Inject

class SharedPrefernceManager @Inject constructor(private val sharedPrefrences:SharedPreferences) :ISharedPrefrenceManager {
    companion object{
        const val SHARED_PREFERENCE_NAME="shared_pref"
        const val KEY_IS_FIRST_TIME="is_first_time"
        const val USER_IS_LOGGED_IN = "USER_IS_LOGGED_IN"
        private const val KEY_REGISTER_RESPONSE = "register_response_key"



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

    override fun saveRegisterResponse(response: RegisterPassengerResponse?) {
        val jsonString = Gson().toJson(response)
        // Save to shared preferences
        sharedPrefrences.edit().putString(KEY_REGISTER_RESPONSE, jsonString).apply()
    }


    override fun getRegisterResponse(): RegisterPassengerResponse? {
        val jsonString = sharedPrefrences.getString(KEY_REGISTER_RESPONSE, null)
        // Deserialize the JSON string back to the object
        return Gson().fromJson(jsonString, RegisterPassengerResponse::class.java)
    }

    override fun deleteAccessToken(): Boolean {
        sharedPrefrences.edit().remove(Constants.USER_IS_LOGGED_IN).apply()
        return userIsLoggedIn()
    }
}