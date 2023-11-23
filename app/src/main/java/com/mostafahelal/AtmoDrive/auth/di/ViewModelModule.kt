package com.mostafahelal.AtmoDrive.auth.di

import com.mostafahelal.AtmoDrive.Utils.MySharedPreferences
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    @Singleton
    fun providesSplashViewModel(mySharedPreferences: MySharedPreferences) : SplashViewModel{
        return SplashViewModel(mySharedPreferences)
    }

}