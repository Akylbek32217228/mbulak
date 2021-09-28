package com.e.mbulak.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TrafficSource(
    @Json(name = "id")
    val id : String,
    @Json(name = "name")
    val name : String) {
    override fun toString(): String {
        return name
    }
}

