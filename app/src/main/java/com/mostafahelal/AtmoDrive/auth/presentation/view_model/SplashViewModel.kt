package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import androidx.lifecycle.ViewModel
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.MySharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SplashViewModel @Inject constructor(
  private val mySharedPreferences: MySharedPreferences
) :ViewModel() {
    val loggedIn:Boolean=mySharedPreferences.userIsLoggedIn()
    val isFirstTime:Boolean=mySharedPreferences.isFirstAppLaunch()
    fun saveIsFirstTime (isFirst:Boolean)=mySharedPreferences.saveFirstAppLaunch(isFirst)


}