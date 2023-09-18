package com.mostafahelal.AtmoDrive.auth.di

import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import com.mostafahelal.AtmoDrive.auth.domain.use_case.AuthUseCase
import com.mostafahelal.AtmoDrive.auth.domain.use_case.IAuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaeModule {
    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository): IAuthUseCase {
        return AuthUseCase(authRepository=authRepository)
    }
}