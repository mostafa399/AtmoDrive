package com.mostafahelal.AtmoDrive.maps.domain.model

import com.google.gson.annotations.SerializedName

data class DataTrip(
                    val dropoffLat: String,
                    val dropoffLng: String,
                    val dropoffLocationName: String,
                    val pickupLat: String,
                    val pickupLng: String,
                    val pickupLocationName: String,
                    val startDate: Any? = null,
                    val tripId: Int,
                    val tripStatus: String,
                    val car_brand: String,
                    val car_model: String
){

}
