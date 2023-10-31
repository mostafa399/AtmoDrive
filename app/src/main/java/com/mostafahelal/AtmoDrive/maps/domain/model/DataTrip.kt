package com.mostafahelal.AtmoDrive.maps.domain.model

import com.google.gson.annotations.SerializedName

data class DataTrip(
                    val dropoffLat: String? = null,
                    val dropoffLng: String? = null,
                    val dropoffLocationName: String? = null,
                    val pickupLat: String? = null,
                    val pickupLng: String? = null,
                    val pickupLocationName: String? = null,
                    val startDate: Any? = null,
                    val tripId: Int? = null,
                    val tripStatus: String? = null){

}
