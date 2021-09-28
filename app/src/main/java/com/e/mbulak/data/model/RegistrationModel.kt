package com.e.mbulak.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationModel(
    @Json(name = "code")
    val code : Int,
    @Json(name = "result")
    val result : Registration?,
    @Json(name = "error")
    val error : Error?
)
