package com.gturedi.socialnetworkapp

object AppConst {

    val AUTH_URL = "https://foursquare.com/oauth2/authenticate" +
            "?client_id=${BuildConfig.CLIENT_ID}" +
            "&response_type=code" +
            "&redirect_uri=${BuildConfig.REDIRECT_URL}"
    val API_URL = "https://api.foursquare.com/v2/"
}