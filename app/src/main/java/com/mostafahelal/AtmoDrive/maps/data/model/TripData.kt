package com.mostafahelal.AtmoDrive.maps.data.model


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.DataTrip

data class TripData(
    @SerializedName("dropoff_lat")
    val dropoffLat: String,
    @SerializedName("dropoff_lng")
    val dropoffLng: String,
    @SerializedName("dropoff_location_name")
    val dropoffLocationName: String,
    @SerializedName("pickup_lat")
    val pickupLat: String,
    @SerializedName("pickup_lng")
    val pickupLng: String ,
    @SerializedName("pickup_location_name")
    val pickupLocationName: String,
    @SerializedName("start_date")
    val startDate: Any? = null,
    @SerializedName("trip_id")
    val tripId: Int,
    @SerializedName("trip_status")
    val tripStatus: String,
    @SerializedName("car_brand")
    val car_brand: String,
    @SerializedName("car_model")
    val car_model: String
){
    fun asDomain():DataTrip{
        return DataTrip(dropoffLat, dropoffLng, dropoffLocationName, pickupLat, pickupLng,
            pickupLocationName, startDate, tripId,tripStatus,car_brand,car_model)
    }
}