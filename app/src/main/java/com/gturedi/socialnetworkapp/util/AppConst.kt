package com.gturedi.socialnetworkapp.util

import com.gturedi.socialnetworkapp.BuildConfig

object AppConst {

    const val URL_AUTH = "https://foursquare.com/oauth2/authenticate" +
            "?client_id=${BuildConfig.CLIENT_ID}" +
            "&redirect_uri=${BuildConfig.REDIRECT_URL}" +
            "&response_type=code"

    const val SERVICE_URL_AUTH = "https://foursquare.com/oauth2/access_token/"

    const val SERVICE_URL_SOCIAL_NETWORK = "https://api.foursquare.com/v2/"

    const val CONN_TIMEOUT = 60L

    const val API_VERS = "20190425"

    const val GRANT_TYPE = "authorization_code"

}