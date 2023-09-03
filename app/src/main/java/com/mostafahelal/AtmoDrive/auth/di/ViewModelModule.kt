package com.mostafahelal.AtmoDrive.auth.di

import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.SharedPrefernceManager
import com.mostafahelal.AtmoDrive.auth.domain.use_case.AuthUseCase
import com.mostafahelal.AtmoDrive.auth.domain.use_case.IAuthUseCase
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SendPhoneViewModel
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    fun providesSplashViewModel(sharedPrefernceManager: SharedPrefernceManager) : SplashViewModel{
        return SplashViewModel(sharedPrefernceManager)
    }

}