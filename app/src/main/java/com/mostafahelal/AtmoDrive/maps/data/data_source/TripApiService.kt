package com.mostafahelal.AtmoDrive.maps.data.data_source

import com.mostafahelal.AtmoDrive.maps.data.model.CaptainDetailsResponse
import com.mostafahelal.AtmoDrive.maps.data.model.ConfirmTripResponse
import com.mostafahelal.AtmoDrive.maps.data.model.MakeTripResponse
import com.mostafahelal.AtmoDrive.maps.data.model.TripDetailsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TripApiService {
    @POST("make-trip")
    @FormUrlEncoded
    suspend fun makeTrip(
        @Field("distance_text") distanceText: String,
        @Field("distance_value") distanceValue: Long,
        @Field("duration_text") durationText: String,
        @Field("duration_value") durationValue: Long
    ):MakeTripResponse
    @POST("confirm-trip")
    @FormUrlEncoded
    suspend fun confirmTrip(
        @Field("vehicle_class_id") vehicle_class_id: String,
        @Field("pickup_lat") pickup_lat: String,
        @Field("pickup_lng") pickup_lng:String ,
        @Field("dropoff_lat") dropoff_lat: String,
        @Field("dropoff_lng") dropoff_lng: String,
        @Field("estimate_cost") estimate_cost: String,
        @Field("estimate_time") estimate_time: String,
        @Field("estimate_distance") estimate_distance: String,
        @Field("pickup_location_name") pickup_location_name: String,
        @Field("dropoff_location_name") dropoff_location_name: String
    ):ConfirmTripResponse
    @POST("get-captain-details")
    @FormUrlEncoded
    suspend fun getCaptainDetails(
        @Field("trip_id") trip_id: Int,
    ): CaptainDetailsResponse
    @POST("get-trip-details")
    @FormUrlEncoded
    suspend fun getTripDetails(
        @Field("trip_id") trip_id: Int,
    ): TripDetailsResponse


}