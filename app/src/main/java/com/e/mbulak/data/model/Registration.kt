package com.e.mbulak.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Registration(

    @Json(name = "id")
    val id : String,
    @Json(name = "login")
    val login : String,
    @Json(name = "password")
    val password : String,
    @Json(name = "message")
    val message : String


)
