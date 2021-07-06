package com.gturedi.socialnetworkapp.network

data class VenueResponseModel(
    val venue: VenueModel
)

data class VenueModel(
    val id: String,
    val name: String,
    val url: String,
)