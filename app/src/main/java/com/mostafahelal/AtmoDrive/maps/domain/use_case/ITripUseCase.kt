package com.mostafahelal.AtmoDrive.maps.domain.use_case

import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelBeforeCaptainAccept
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.CaptainDetails
import com.mostafahelal.AtmoDrive.maps.domain.model.ConfirmTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.TripDetails

interface ITripUseCase {
    suspend fun makeTrip(distanceText: String,
                         distanceValue: Double,
                         durationText: String,
                         durationValue: Int): Resource<MakeTrip>
    suspend fun confirmTrip(vehicle_class_id: String,
                            pickup_lat: String, pickup_lng: String,
                            dropoff_lat: String, dropoff_lng: String,
                            estimate_cost: String, estimate_time: String,
                            estimate_distance: String, pickup_location_name: String, dropoff_location_name: String
    ): Resource<ConfirmTrip>
    suspend fun getCaptainDetails(
        trip_id: Int,
    ): Resource<CaptainDetails>
    suspend fun getTripDetails(
        trip_id: Int,
    ): Resource<TripDetails>
    suspend fun cancelTripBeforeCaptainAccepts(
        trip_id: Int,
    ): Resource<CancelBeforeCaptainAccept>
    suspend fun cancelTrip(
        trip_id: Int,
    ): Resource<CancelTrip>

    suspend fun onTrip():Resource<TripDetails>


}