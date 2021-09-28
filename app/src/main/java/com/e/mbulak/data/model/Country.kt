package com.e.mbulak.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "id")
    val id : Int,
    @Json(name = "name")
    val name : String,
    @Json(name = "iso_code")
    val iso_code : String,
    @Json(name = "phone_code")
    val phone_code : String,
    @Json(name = "phone_length")
    val phone_length : String,
    @Json(name = "phone_mask")
    val phone_mask : String,
    @Json(name = "phone_mask_small")
    val phone_mask_small : String


) {

    override fun toString(): String {
        return name
    }

}
