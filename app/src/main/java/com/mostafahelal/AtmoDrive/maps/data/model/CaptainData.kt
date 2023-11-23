package com.mostafahelal.AtmoDrive.maps.data.model

import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.DataCap
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTripData

data class CaptainData(
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("blood_type")
    val bloodType: Any? = null,
    @SerializedName("captain_code")
    val captainCode: String? = null,
    @SerializedName("dropoff_lat")
    val dropoffLat: String? = null,
    @SerializedName("dropoff_lng")
    val dropoffLng: String? = null,
    @SerializedName("dropoff_location_name")
    val dropoffLocationName: String? = null,
    @SerializedName("estimate_cost")
    val estimateCost: Int? = null,
    @SerializedName("estimate_time")
    val estimateTime: Int? = null,
    @SerializedName("full_name")
    val fullName: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("mobile")
    val mobile: String? = null,
    @SerializedName("pickup_lat")
    val pickupLat: String? = null,
    @SerializedName("pickup_lng")
    val pickupLng: String? = null,
    @SerializedName("pickup_location_name")
    val pickupLocationName: String? = null,
    @SerializedName("rate")
    val rate: Int? = null,
    @SerializedName("trip_color")
    val tripColor: String? = null,
    @SerializedName("vehicle")
    val vehicle: String? = null,
    @SerializedName("vehicle_class")
    val vehicleClass: Any? = null,
    @SerializedName("vehicle_model")
    val vehicleModel: String? = null,
    @SerializedName("vehicle_registration_plate")
    val vehicleRegistrationPlate: String? = null
){
    fun asDomain() : DataCap{
        return DataCap(dropoffLat, dropoffLng, dropoffLocationName, estimateCost,
            estimateTime, fullName, mobile, pickupLat, pickupLng,pickupLocationName,tripColor,avatar)
    }
}
