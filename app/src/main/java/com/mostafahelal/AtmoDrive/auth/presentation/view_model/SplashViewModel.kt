package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreference: SharedPreference)
    :ViewModel() {
        val loggedIn:Boolean=sharedPreference.userIsLoggedIn()

}