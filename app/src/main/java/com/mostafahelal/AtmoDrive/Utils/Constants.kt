package com.mostafahelal.AtmoDrive.Utils

import com.google.android.gms.maps.model.LatLng

class Constants {
    companion object {
        const val BASE_Image_URL = "https://s1.drive.dashboard.atmosphere.solutions/storage/"

        const val SHARED_PREFERENCE_NAME = "PASSENGER"
        const val IS_FIRST_APP_LAUNCH = "is_first_time"
        const val USER_IS_LOGGED_IN = "USER_IS_LOGGED_IN"
        const val TOKEN = "TOKEN"
        const val STATUS = "STATUS"
        const val RATE = "RATE"
        const val ISDARKMODE = "ISDARKMODE"
        const val SUSPEND = "SUSPEND"
        const val AVATAR = "AVATAR"
        const val EMAIL = "EMAIL"
        const val FULLNAME = "FULLNAME"
        const val LANG = "LANG"
        const val PASSENGERCODE = "PASSENGERCODE"
        const val MOBILE = "MOBILE"
        const val CaptainName = "CaptainMame"
        const val CaptainCost = "CaptainCost"
        const val CaptainMobile = "CaptainMobile"
        const val CAptainAvatar = "CAptainAvatar"
        const val TRIPID = "TRIPID"
        var isBottomSheetOn = false
        var pickUpLatLng: LatLng? = null
        var dropOffLatLng: LatLng? = null
        var tripId: Int = 0
    }



}