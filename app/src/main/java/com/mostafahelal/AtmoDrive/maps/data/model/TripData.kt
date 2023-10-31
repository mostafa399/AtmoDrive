package com.mostafahelal.AtmoDrive.maps.data.model


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.DataTrip

data class TripData(
    @SerializedName("dropoff_lat")
    val dropoffLat: String? = null,
    @SerializedName("dropoff_lng")
    val dropoffLng: String? = null,
    @SerializedName("dropoff_location_name")
    val dropoffLocationName: String? = null,
    @SerializedName("pickup_lat")
    val pickupLat: String? = null,
    @SerializedName("pickup_lng")
    val pickupLng: String? = null,
    @SerializedName("pickup_location_name")
    val pickupLocationName: String? = null,
    @SerializedName("start_date")
    val startDate: Any? = null,
    @SerializedName("trip_id")
    val tripId: Int? = null,
    @SerializedName("trip_status")
    val tripStatus: String? = null
){
    fun asDomain():DataTrip{
        return DataTrip(dropoffLat, dropoffLng, dropoffLocationName, pickupLat, pickupLng,
            pickupLocationName, startDate, tripId)
    }
}