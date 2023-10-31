package com.mostafahelal.AtmoDrive.maps.di

import com.mostafahelal.AtmoDrive.maps.data.data_source.ITripDataSource
import com.mostafahelal.AtmoDrive.maps.data.data_source.TripApiService
import com.mostafahelal.AtmoDrive.maps.data.data_source.TripDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TripDataSourceModule {
    @Provides
    fun getTripDataSource(tripApiService: TripApiService): ITripDataSource
            = TripDataSource(tripApiService)

}