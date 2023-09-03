package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.ISharedPrefrenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SplashViewModel @Inject constructor(
    sharedPrefrenceManager: ISharedPrefrenceManager
) :ViewModel() {
    val loggedIn:Boolean=sharedPrefrenceManager.userIsLoggedIn()


}