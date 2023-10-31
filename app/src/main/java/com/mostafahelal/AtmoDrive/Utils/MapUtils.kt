package com.mostafahelal.AtmoDrive.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.gms.maps.model.LatLng
import com.mostafahelal.AtmoDrive.R
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin

object MapUtils {

    fun getCarBitmap(context: Context): Bitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_car)
        return Bitmap.createScaledBitmap(bitmap, 65, 100, false)
    }

    fun getMarkerForSeclctionPosition(context: Context): Bitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.customarker)
        return Bitmap.createScaledBitmap(bitmap, 50, 100, false)
    }

    fun getOriginDestinationMarkerBitmap(context: Context): Bitmap {
//        val height = 20
//        val width = 20
//        val bitmap = Bitmap.createBitmap(height, width, Bitmap.Config.RGB_565)
//        val canvas = Canvas(bitmap)
//        val paint = Paint()
//        paint.color = Color.parseColor("#543CC6")
//        paint.style = Paint.Style.FILL
//        paint.isAntiAlias = true
//        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
        //   canvas.drawCircle(0F, 0F, width.toFloat(), height.toFloat(), paint)
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.customarker)
        return Bitmap.createScaledBitmap(bitmap, 50, 100, false)
        return bitmap
    }
//    fun getOriginDestinationMarkerBitmap(context: Context): Bitmap {
//        val height = 40
//        val width = 40
//        val bitmap = Bitmap.createBitmap(height, width, Bitmap.Config.ALPHA_8)
//        val canvas = Canvas(bitmap)
//        val paint = Paint()
//        paint.color = Color.parseColor("#0064fe")
//        paint.style = Paint.Style.FILL
//        paint.isAntiAlias = true
//        val filter: ColorFilter = PorterDuffColorFilter(
//            ContextCompat.getColor(context, R.color.blue),
//            PorterDuff.Mode.SRC_IN
//        )
//        paint.colorFilter = filter
//        // canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
//        canvas.drawCircle(20F, 20F, 20F, paint)
//
//        return bitmap
//    }

    fun getRotation(start: LatLng, end: LatLng): Float {
        val latDifference: Double = abs(start.latitude - end.latitude)
        val lngDifference: Double = abs(start.longitude - end.longitude)
        var rotation = -1F
        when {
            start.latitude < end.latitude && start.longitude < end.longitude -> {
                rotation = Math.toDegrees(atan(lngDifference / latDifference)).toFloat()
            }
            start.latitude >= end.latitude && start.longitude < end.longitude -> {
                rotation = (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 90).toFloat()
            }
            start.latitude >= end.latitude && start.longitude >= end.longitude -> {
                rotation = (Math.toDegrees(atan(lngDifference / latDifference)) + 180).toFloat()
            }
            start.latitude < end.latitude && start.longitude >= end.longitude -> {
                rotation =
                    (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 270).toFloat()
            }
        }
        return rotation
    }

    /**
     * This function returns the list of locations of Car during the trip i.e. from Origin to Destination
     */
    fun getListOfLocations(): ArrayList<LatLng> {
        val locationList = ArrayList<LatLng>()
        locationList.add(LatLng(28.436970000000002, 77.11272000000001))
        locationList.add(LatLng(28.43635, 77.11289000000001))
        locationList.add(LatLng(28.4353, 77.11317000000001))
        locationList.add(LatLng(28.435280000000002, 77.11332))
        locationList.add(LatLng(28.435350000000003, 77.11368))
        locationList.add(LatLng(28.4356, 77.11498))
        locationList.add(LatLng(28.435660000000002, 77.11519000000001))
        locationList.add(LatLng(28.43568, 77.11521))
        locationList.add(LatLng(28.436580000000003, 77.11499))
        locationList.add(LatLng(28.436590000000002, 77.11507))
        return locationList
    }

    fun distance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
    ): Double {
        return if (lat1 == lat2 && lon1 == lon2) {
            0.0
        } else {
            val theta = lon1 - lon2
            var dist =
                sin(Math.toRadians(lat1)) * sin(
                    Math.toRadians(lat2)
                ) + cos(Math.toRadians(lat1)) * cos(
                    Math.toRadians(
                        lat2
                    )
                ) * cos(Math.toRadians(theta))
            dist = acos(dist)

            dist = Math.toDegrees(dist)
            dist *= 60 * 1.1515


            dist *= 1.609344

            dist* 1000 //meter
        }
    }

}