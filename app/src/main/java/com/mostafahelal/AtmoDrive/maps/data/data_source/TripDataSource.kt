package com.mostafahelal.AtmoDrive.maps.data.data_source

import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelBeforeCaptainAccept
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.CaptainDetails
import com.mostafahelal.AtmoDrive.maps.domain.model.ConfirmTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.TripDetails
import javax.inject.Inject

class TripDataSource @Inject constructor( private val tripApiService: TripApiService):ITripDataSource {
    override suspend fun makeTrip(
        distanceText: String,
        distanceValue: Double,
        durationText: String,
        durationValue: Int
    ): Resource<MakeTrip> {
        return try {
            val makeTripData = tripApiService.makeTrip(distanceText,distanceValue,durationText,durationValue)
            if (makeTripData.status){
                return Resource.Success(makeTripData.asDomain())
            }else{
                return Resource.Error(makeTripData.message)
            }
        }catch (e: Exception){
            Resource.Error(NetworkState.getErrorMessage(e).msg.toString())
        }
    }

    override suspend fun confirmTrip(vehicle_class_id: String,
        pickup_lat: String, pickup_lng: String,
        dropoff_lat: String, dropoff_lng: String,
        estimate_cost: String, estimate_time: String,
        estimate_distance: String, pickup_location_name: String, dropoff_location_name: String
    ): Resource<ConfirmTrip> {
        return try {
            val confirmTripData = tripApiService.confirmTrip( vehicle_class_id, pickup_lat,
                pickup_lng, dropoff_lat,
                dropoff_lng, estimate_cost, estimate_time,
                estimate_distance, pickup_location_name, dropoff_location_name)
            if (confirmTripData.status){
                return Resource.Success(confirmTripData.asDomain())
            }else{
                return Resource.Error(confirmTripData.message)
            }
        }catch (e: Exception){
            Resource.Error(NetworkState.getErrorMessage(e).msg.toString())
        }
    }

    override suspend fun getCaptainDetails(trip_id: Int): Resource<CaptainDetails> {
        return try {
            val captainDetails = tripApiService.getCaptainDetails(trip_id)
            if (captainDetails.status){
                return Resource.Success(captainDetails.asDomain())
            }else{
                return Resource.Error(captainDetails.message)
            }
        }catch (e: Exception){
            Resource.Error(NetworkState.getErrorMessage(e).msg.toString())
        }    }

    override suspend fun getTripDetails(trip_id: Int): Resource<TripDetails> {
        return try {
            val tripDetails = tripApiService.getTripDetails(trip_id)
            if (tripDetails.status){
                return Resource.Success(tripDetails.asDomain())
            }else{
                return Resource.Error(tripDetails.message)
            }
        }catch (e: Exception){
            Resource.Error(NetworkState.getErrorMessage(e).msg.toString())
        }      }

    override suspend fun cancelTripBeforeCaptainAccepts(trip_id: Int): Resource<CancelBeforeCaptainAccept> {
        return try {
            val tripDetails = tripApiService.cancelTripBeforeCaptainAccepts(trip_id)
            if (tripDetails.status){
                return Resource.Success(tripDetails.asDomain())
            }else{
                return Resource.Error(tripDetails.message)
            }
        }catch (e: Exception){
            Resource.Error(NetworkState.getErrorMessage(e).msg.toString())
        }
    }

    override suspend fun cancelTrip(trip_id: Int): Resource<CancelTrip> {
        return try {
            val tripDetails = tripApiService.cancelTrip(trip_id)
            if (tripDetails.status){
                return Resource.Success(tripDetails.asDomain())
            }else{
                return Resource.Error(tripDetails.message)
            }
        }catch (e: Exception){
            Resource.Error(NetworkState.getErrorMessage(e).msg.toString())
        }
    }

    override suspend fun onTrip(): Resource<TripDetails> {
        return try {
            val tripDetails = tripApiService.onTrip()
            if (tripDetails.status){
                return Resource.Success(tripDetails.asDomain())
            }else{
                return Resource.Error(tripDetails.message)
            }
        }catch (e: Exception){
            Resource.Error(NetworkState.getErrorMessage(e).msg.toString())
        }
    }
}