package com.e.mbulak.data

import com.e.mbulak.data.model.NewUserRegistrationModel
import com.e.mbulak.data.remote.Api
import retrofit2.http.Part


class Repository constructor(private val api: Api) {

    suspend fun checkPhoneNumber(number : String) = api.checkPhone(number)

    suspend fun checkCode(id : Int, code : Int) = api.checkCode(id, code)

    suspend fun listAvailableCountry(id : Int) = api.listAvailableCountry(id)

    suspend fun listSecretQuestion(id : Int) = api.listSecretQuestion(id)

    suspend fun login(login : String, password : String, uid : String, appid : String, system : Int) = api.login(
        login = login, password = password, uid = uid, appid = appid, system = system
    )

    suspend fun registration(body : NewUserRegistrationModel) = api.registration( last_name = body.last_name,
        first_name = body.first_name,
        second_name = body.second_name,
        u_date = body.u_date,
        gender = body.gender,
        nationality = body.nationality,
        first_phone = body.first_phone,
        second_phone = body.second_phone,
        question = body.question,
        response = body.response ,
        traffic_source = body.traffic_source,
        sms_code = body.sms_code,
        system = body.system,
    )

    suspend fun listGender(id : Int) = api.listGender(id)

    suspend fun listNationalities(id : Int) = api.listNationality(id)

    suspend fun listTrafficSource(id : Int) = api.listTrafficSource(id)
}