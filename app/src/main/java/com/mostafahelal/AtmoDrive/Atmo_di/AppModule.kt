package com.mostafahelal.AtmoDrive.Atmo_di

import android.content.Context
import android.content.SharedPreferences
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.MySharedPreferences
import com.mostafahelal.AtmoDrive.Utils.ISharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            Constants.SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }
    @Singleton
    @Provides
    fun providesSharedPreferencesManager(sharedPreferences: SharedPreferences): ISharedPreferencesManager {
        return MySharedPreferences(sharedPreferences)
    }
}