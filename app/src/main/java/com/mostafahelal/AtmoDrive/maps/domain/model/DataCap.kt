package com.mostafahelal.AtmoDrive.maps.domain.model

import com.google.gson.annotations.SerializedName

data class DataCap(
    val dropoffLat: String? = null,
    val dropoffLng: String? = null,
    val dropoffLocationName: String? = null,
    val estimateCost: Int? = null,
    val estimateTime: Int? = null,
    val fullName: String? = null,
    val mobile: String? = null,
    val pickupLat: String? = null,
    val pickupLng: String? = null,
    val pickupLocationName: String? = null,
    val tripColor: String? = null

    )
