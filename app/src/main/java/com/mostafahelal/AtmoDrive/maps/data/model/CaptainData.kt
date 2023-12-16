package com.mostafahelal.AtmoDrive.maps.data.model

import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.DataCap
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTripData

data class CaptainData(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("captain_code")
    val captainCode: String,
    @SerializedName("dropoff_lat")
    val dropoffLat: String,
    @SerializedName("dropoff_lng")
    val dropoffLng: String,
    @SerializedName("dropoff_location_name")
    val dropoffLocationName: String,
    @SerializedName("estimate_cost")
    val estimateCost: Int,
    @SerializedName("estimate_time")
    val estimateTime: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("pickup_lat")
    val pickupLat: String,
    @SerializedName("pickup_lng")
    val pickupLng: String,
    @SerializedName("pickup_location_name")
    val pickupLocationName: String,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("trip_color")
    val tripColor: String,
    @SerializedName("vehicle_class")
    val vehicleClass: String? = null,
    @SerializedName("vehicle_model")
    val vehicleModel: String,
    @SerializedName("vehicle_registration_plate")
    val vehicleRegistrationPlate: String
){
    public fun asDomain() : DataCap{
        return DataCap(dropoffLat, dropoffLng, dropoffLocationName, estimateCost,
            estimateTime, fullName, mobile, pickupLat, pickupLng,pickupLocationName,tripColor,avatar)
    }
}
