package com.gturedi.socialnetworkapp.util

import com.gturedi.socialnetworkapp.BuildConfig

object AppConst {

    const val URL_BASE = "https://foursquare.com/oauth2/"

    const val URL_AUTH = "$URL_BASE/authenticate" +
            "?client_id=${BuildConfig.CLIENT_ID}" +
            "&redirect_uri=${BuildConfig.REDIRECT_URL}" +
            "&response_type=code"

    const val URL_API = "https://api.foursquare.com/v2/"

    const val CONN_TIMEOUT = 60L

    const val API_VERS = "20190425"

    const val GRANT_TYPE = "authorization_code"

}