package com.mostafahelal.AtmoDrive.auth.di

import com.mostafahelal.AtmoDrive.auth.data.data_source.remote.IRemoteAuth
import com.mostafahelal.AtmoDrive.auth.data.repository.AuthRepositoryImpl
import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepositoryModule(iRemoteAuth: IRemoteAuth):AuthRepository{
        return AuthRepositoryImpl(iRemoteAuth=iRemoteAuth)
    }
}