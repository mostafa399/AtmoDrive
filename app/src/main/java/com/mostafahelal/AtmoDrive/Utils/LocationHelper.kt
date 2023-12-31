package com.mostafahelal.AtmoDrive.Utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
object LocationHelper {

    fun getEstimatedTime(location1: LatLng, location2: LatLng): Int {
        val dist = getEstimatedDistance(
            location1,
            location2
        )

        return ((dist / 50) * 60).toInt()
    }

    fun getEstimatedDistance(
        pickUpLocation: LatLng,
        dropOffLocation: LatLng
    ): Double {
        return if (pickUpLocation.latitude == dropOffLocation.latitude && pickUpLocation.longitude == dropOffLocation.longitude) {
            0.0
        } else {
            val theta = pickUpLocation.longitude - dropOffLocation.longitude
            var distance =
                sin(Math.toRadians(pickUpLocation.latitude)) * sin(
                    Math.toRadians(dropOffLocation.latitude)
                ) + cos(Math.toRadians(pickUpLocation.latitude)) * cos(
                    Math.toRadians(
                        dropOffLocation.latitude
                    )
                ) * cos(Math.toRadians(theta))
            distance = acos(distance)

            distance = Math.toDegrees(distance)
            distance *= 60 * 1.1515


            distance *= 1.609344

            distance
        }
    }

    fun decodePoly(encoded: String?): List<LatLng> {
        val poly: MutableList<LatLng> = ArrayList()
        var index = 0
        val len = encoded?.length
        var lat = 0
        var lng = 0
        while (index < len!!) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }
        return poly
    }

    fun generateBitmapDescriptorFromRes(
        context: Context?, resId: Int
    ): Bitmap? {
        val drawable = ContextCompat.getDrawable(context!!, resId)
        drawable!!.setBounds(
            0,
            0,
            drawable.intrinsicWidth,
            drawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        return bitmap
    }


     fun getBearing(begin: LatLng, end: LatLng): Float {
        val dLon = end.longitude - begin.longitude
        val x = Math.sin(Math.toRadians(dLon)) * Math.cos(Math.toRadians(end.latitude))
        val y = (Math.cos(Math.toRadians(begin.latitude)) * Math.sin(Math.toRadians(end.latitude))
                - Math.sin(Math.toRadians(begin.latitude)) * Math.cos(Math.toRadians(end.latitude)) * Math.cos(
            Math.toRadians(dLon)
        ))
        val bearing = Math.toDegrees(Math.atan2(x, y))
        return bearing.toFloat()
    }
}

//    suspend fun drawPolyline(response: DirectionResponse): PolylineOptions {
//
//        val shape = response.routes[0].overview_polyline.points
//        return PolylineOptions()
//            .addAll(PolyUtil.decode(shape))
//            .width(10f)
//            .color(Color.BLUE)
//
//    }




//    suspend fun getDirectionsApi(
//        str_origin: String,
//        str_dest: String,
//        key: String
////    ): DirectionResponse {
////
//
//        val res = ApiManager.getMapService().getDirection(str_origin, str_dest, key)

//
//        return res