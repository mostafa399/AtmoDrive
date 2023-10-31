package com.mostafahelal.AtmoDrive.maps.data.model

import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTripData

data class CaptainData(
    @SerializedName("estimate_cost")
    val estimate_cost: String,
    @SerializedName("estimate_distance")
    val estimate_distance: Double,
    @SerializedName("estimate_time")
    val estimate_time: Int,
    @SerializedName("vehicle_class_icon")
    val vehicle_class_icon: String,
    @SerializedName("vehicle_class_name")
    val vehicle_class_name: String
){
    fun asDomain() = MakeTripData(
        estimate_cost,
        estimate_distance,
        estimate_time,
        vehicle_class_icon,
        vehicle_class_name)
}
