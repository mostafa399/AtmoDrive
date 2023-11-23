package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import androidx.lifecycle.ViewModel
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.ISharedPreferencesManager
import com.mostafahelal.AtmoDrive.Utils.MySharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SplashViewModel @Inject constructor(
    iSharedPreferencesManager: ISharedPreferencesManager
) :ViewModel() {
    val loggedIn:Boolean=iSharedPreferencesManager.userIsLoggedIn()
    val token=iSharedPreferencesManager.getString(Constants.TOKEN)

}