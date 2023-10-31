package com.mostafahelal.AtmoDrive.maps.data.data_source

import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.maps.data.model.CaptainDetailsResponse
import com.mostafahelal.AtmoDrive.maps.data.model.ConfirmTripResponse
import com.mostafahelal.AtmoDrive.maps.data.model.MakeTripResponse
import com.mostafahelal.AtmoDrive.maps.data.model.TripDetailsResponse
import com.mostafahelal.AtmoDrive.maps.domain.model.CaptainDetails
import com.mostafahelal.AtmoDrive.maps.domain.model.ConfirmTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.TripDetails
import retrofit2.http.Field

interface ITripDataSource {
    suspend fun makeTrip(distanceText: String,
                         distanceValue: Long,
                         durationText: String,
                         durationValue: Long): Resource<MakeTrip>

    suspend fun confirmTrip(
           vehicle_class_id: String,
            pickup_lat: String,
            pickup_lng:String,
            dropoff_lat: String,
            dropoff_lng: String,
            estimate_cost: String,
            estimate_time: String,
            estimate_distance: String,
            pickup_location_name: String,
            dropoff_location_name: String
    ): Resource<ConfirmTrip>

    suspend fun getCaptainDetails(
        trip_id: Int,
    ): Resource<CaptainDetails>
    suspend fun getTripDetails(
        trip_id: Int,
    ): Resource<TripDetails>


}