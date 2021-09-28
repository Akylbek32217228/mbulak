package com.e.mbulak.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @field:Json(name = "id")
    val id : Int?,
    @field:Json(name = "code")
    val code : Int?,
    @field:Json(name = "message")
    val message : String?,
    @field:Json(name = "login")
    val login : String?
)
