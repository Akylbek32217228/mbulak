package com.e.mbulak.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResult(
    @field:Json(name = "code")
    val code : Int?,
    @field:Json(name = "result")
    val result : Result?,
    @field:Json(name = "error")
    val error : Error?
)
