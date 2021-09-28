package com.e.mbulak.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NewUserRegistrationModel(
    @field:Json(name = "last_name")
    val last_name : String?,
    @field:Json(name = "first_name")
    val first_name : String?,
    @field:Json(name = "second_name")
    val second_name : String?,
    @field:Json(name = "u_date")
    val u_date : Date?,
    @field:Json(name = "gender")
    val gender : Int?,
    @field:Json(name = "nationality")
    val nationality : Int?,
    @field:Json(name = "first_phone")
    val first_phone : String?,
    @field:Json(name = "second_phone")
    val second_phone : String?,
    @field:Json(name = "question")
    val question : Int?,
    @field:Json(name = "response")
    val response : String?,
    @field:Json(name = "traffic_source")
    val traffic_source : Int?,
    @field:Json(name = "sms_code")
    val sms_code : String?,
    @field:Json(name = "system")
    val system : Int?
    ) {




}
