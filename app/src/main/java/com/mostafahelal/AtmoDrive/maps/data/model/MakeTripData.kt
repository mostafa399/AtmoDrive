package com.mostafahelal.AtmoDrive.maps.data.model


import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTripData

data class MakeTripData(
    @SerializedName("estimate_cost")
    val estimateCost: String,
    @SerializedName("estimate_distance")
    val estimateDistance: Double,
    @SerializedName("estimate_time")
    val estimateTime: Int,
    @SerializedName("vehicle_class_icon")
    val vehicleClassIcon: String,
    @SerializedName("vehicle_class_name")
    val vehicleClassName: String
){
    fun asDomain():MakeTripData{
        return MakeTripData(estimateCost, estimateDistance, estimateTime, vehicleClassIcon, vehicleClassName)
    }
}