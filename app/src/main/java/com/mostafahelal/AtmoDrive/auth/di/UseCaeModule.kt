package com.mostafahelal.AtmoDrive.auth.di

import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import com.mostafahelal.AtmoDrive.auth.domain.use_case.AuthUseCase
import com.mostafahelal.AtmoDrive.auth.domain.use_case.IAuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaeModule {
    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository): IAuthUseCase {
        return AuthUseCase(authRepository=authRepository)
    }
}