package com.mostafahelal.AtmoDrive.maps.domain.use_case

import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelBeforeCaptainAccept
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.CaptainDetails
import com.mostafahelal.AtmoDrive.maps.domain.model.ConfirmTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.TripDetails
import com.mostafahelal.AtmoDrive.maps.domain.repository.ITripRepository
import javax.inject.Inject

class TripUseCase  @Inject constructor(private val iMakeTripRepository: ITripRepository) : ITripUseCase{
    override suspend fun makeTrip(
        distanceText: String,
        distanceValue: Double,
        durationText: String,
        durationValue: Int
    ): Resource<MakeTrip> {
        return iMakeTripRepository.makeTrip(distanceText, distanceValue, durationText, durationValue)
    }

    override suspend fun confirmTrip(
        vehicle_class_id: String,
        pickup_lat: String,
        pickup_lng: String,
        dropoff_lat: String,
        dropoff_lng: String,
        estimate_cost: String,
        estimate_time: String,
        estimate_distance: String,
        pickup_location_name: String,
        dropoff_location_name: String
    ): Resource<ConfirmTrip> {
        return iMakeTripRepository.confirmTrip( vehicle_class_id, pickup_lat,
            pickup_lng, dropoff_lat,
            dropoff_lng, estimate_cost, estimate_time,
            estimate_distance, pickup_location_name, dropoff_location_name)    }

    override suspend fun getCaptainDetails(trip_id: Int): Resource<CaptainDetails> {
        return iMakeTripRepository.getCaptainDetails(trip_id)
    }

    override suspend fun getTripDetails(trip_id: Int): Resource<TripDetails> {
        return iMakeTripRepository.getTripDetails(trip_id)
    }

    override suspend fun cancelTripBeforeCaptainAccepts(trip_id: Int): Resource<CancelBeforeCaptainAccept> {
        return iMakeTripRepository.cancelTripBeforeCaptainAccepts(trip_id)
    }

    override suspend fun cancelTrip(trip_id: Int): Resource<CancelTrip> {
        return iMakeTripRepository.cancelTrip(trip_id)
    }

    override suspend fun onTrip(): Resource<TripDetails> {
        return iMakeTripRepository.onTrip()
    }

}