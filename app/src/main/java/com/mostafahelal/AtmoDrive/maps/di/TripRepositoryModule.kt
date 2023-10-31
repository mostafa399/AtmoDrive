package com.mostafahelal.AtmoDrive.maps.di

import com.mostafahelal.AtmoDrive.maps.data.Repository.TripRepository
import com.mostafahelal.AtmoDrive.maps.data.data_source.ITripDataSource
import com.mostafahelal.AtmoDrive.maps.domain.repository.ITripRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TripRepositoryModule {


    @Provides
    fun provideTripRepo(iTripDataSource: ITripDataSource):ITripRepository
            = TripRepository(iTripDataSource)


}