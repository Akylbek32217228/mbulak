package com.e.mbulak.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SecretQuestions(
    @Json(name = "code")
    val code : Int,
    @Json(name = "result")
    val result : List<Question>,
    @field:Json(name = "error")
    val error : Error?

)
