package com.mostafahelal.AtmoDrive.auth.di

import com.mostafahelal.AtmoDrive.auth.data.data_source.remote.ApiServices
import com.mostafahelal.AtmoDrive.auth.data.data_source.remote.IRemoteAuth
import com.mostafahelal.AtmoDrive.auth.data.data_source.remote.RemoteAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideRemoteData(apiServices: ApiServices):IRemoteAuth{
        return RemoteAuth(apiServices=apiServices)
    }


}