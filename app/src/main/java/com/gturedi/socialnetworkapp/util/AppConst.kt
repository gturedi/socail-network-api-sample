package com.gturedi.socialnetworkapp.util

import com.gturedi.socialnetworkapp.BuildConfig

object AppConst {

    const val AUTH_URL = "https://foursquare.com/oauth2/authenticate" +
            "?client_id=${BuildConfig.CLIENT_ID}" +
            "&redirect_uri=${BuildConfig.REDIRECT_URL}" +
            "&response_type=code"

    const val TOKEN_URL = "https://foursquare.com/oauth2/access_token" +
            "?client_id=${BuildConfig.CLIENT_ID}" +
            "&client_secret=${BuildConfig.CLIENT_SECRET}" +
            "&redirect_uri=${BuildConfig.REDIRECT_URL}" +
            "&grant_type=authorization_code"

    const val API_URL = "https://api.foursquare.com/v2/"

    const val CONN_TIMEOUT = 60L

    const val API_VERS = "20190425"

}