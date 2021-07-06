package com.gturedi.socialnetworkapp.network

data class CheckinReponseModel(
    val checkins: ListModel
)

data class ListModel(
    val count: Int,
    val items: List<CheckinModel>
)

data class CheckinModel(
    val id: String,
    val createdAt: String,
    val venue: CheckinVenueModel,
)

data class CheckinVenueModel(
    val id: String,
    val name: String,
)