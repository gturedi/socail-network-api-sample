package com.gturedi.socialnetworkapp.network.model


data class VenueResponseModel(
    val venue: VenueModel
)

data class VenueModel(
    val id: String,
    val name: String,
    val url: String,
)