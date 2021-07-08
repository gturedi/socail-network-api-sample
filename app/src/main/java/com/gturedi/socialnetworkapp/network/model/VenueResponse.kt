package com.gturedi.socialnetworkapp.network.model


data class VenueResponseModel(
    val venue: VenueModel
)

data class VenueModel(
    val id: String?,
    val name: String?,
    val canonicalUrl: String?,
    val categories: List<CategoryModel>?,
    val bestPhoto: BestPhotoModel?,
){
    fun imageUrl() = "${bestPhoto?.prefix}" +
            "width${bestPhoto?.width}" +
            "${bestPhoto?.suffix}"

    fun categories() = categories?.joinToString(", ") { y -> y.name.orEmpty() }
}

data class CategoryModel(
    val id: String?,
    val name: String?,
)

data class BestPhotoModel(
    val prefix: String?,
    val suffix: String?,
    val width: Int?,
)