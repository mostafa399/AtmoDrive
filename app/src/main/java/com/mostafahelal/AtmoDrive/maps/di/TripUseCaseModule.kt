package com.mostafahelal.AtmoDrive.maps.di

import com.mostafahelal.AtmoDrive.maps.domain.repository.ITripRepository
import com.mostafahelal.AtmoDrive.maps.domain.use_case.ITripUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TripUseCaseModule {
    @Provides
    fun MakeTripUseCase(iTripRepo: ITripRepository):ITripUseCase
            = MakeTripUseCase(iTripRepo)

}