package com.mostafahelal.AtmoDrive.maps.domain.model

import com.google.gson.annotations.SerializedName

data class DataCap(
    val dropoffLat: String,
    val dropoffLng: String,
    val dropoffLocationName: String,
    val estimateCost: Int,
    val estimateTime: Int,
    val fullName: String,
    val mobile: String,
    val pickupLat: String,
    val pickupLng: String,
    val pickupLocationName: String,
    val tripColor: String,
    val avatar :String


    )
