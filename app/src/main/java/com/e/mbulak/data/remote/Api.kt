package com.e.mbulak.data.remote

import com.e.mbulak.data.model.*
import com.e.mbulak.utils.CustomMoshiAdapter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.addAdapter
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface Api {

    @Multipart
    @POST("api/app/checkPhone/?token=oYyxhIFgJjAb")
    suspend fun checkPhone(@Part("phone") phone : String) : Response<ApiResult>

    @Multipart
    @POST("api/app/checkCode/?token=oYyxhIFgJjAb")
    suspend fun checkCode(@Part("id") id : Int, @Part("code") code : Int ) : Response<ApiResult>

    @Multipart
    @POST("api/app/registration/?token=oYyxhIFgJjAb")
    suspend fun registration(@Part("last_name") last_name : String?,
                             @Part("first_name") first_name : String?,
                             @Part("second_name") second_name : String?,
                             @Part("u_date") u_date : Date?,
                             @Part("gender") gender : Int?,
                             @Part("nationality") nationality : Int?,
                             @Part("first_phone") first_phone : String?,
                             @Part("second_phone") second_phone : String?,
                             @Part("question") question : Int?,
                             @Part("response") response : String?,
                             @Part("traffic_source") traffic_source : Int?,
                             @Part("sms_code") sms_code : String?,
                             @Part("system") system : Int?,) : Response<RegistrationModel>

    @Multipart
    @POST("api/app/listAvailableCountry/?token=oYyxhIFgJjAb")
    suspend fun listAvailableCountry(@Part("id") id : Int ) : Response<Countries>

    @Multipart
    @POST("api/app/login/?token=oYyxhIFgJjAb")
    suspend fun login(@Part("login") login : String,
                      @Part("password") password : String,
                      @Part("uid") uid : String,
                      @Part("appid") appid : String,
                      @Part("system") system : Int,) : Response<ApiResult>

    @Multipart
    @POST("api/app/listSecretQuestion/?token=oYyxhIFgJjAb")
    suspend fun listSecretQuestion(@Part("id") id : Int ) : Response<SecretQuestions>

    @Multipart
    @POST("api/app/listGender/?token=oYyxhIFgJjAb")
    suspend fun listGender(@Part("id") id : Int ) : Response<Genders>

    @Multipart
    @POST("api/app/listNationality/?token=oYyxhIFgJjAb")
    suspend fun listNationality(@Part("id") id : Int ) : Response<Nationalities>

    @Multipart
    @POST("api/app/listTrafficSource/?token=oYyxhIFgJjAb")
    suspend fun listTrafficSource(@Part("id") id : Int ) : Response<TrafficSources>

    companion object {
        var api: Api? = null

        var moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .add(Date::class.java,  Rfc3339DateJsonAdapter().nullSafe())

        
        fun getInstance() : Api {
            if (api == null) {
                val retrofit = Retrofit.Builder()
                    .client(getUnsafeOkHttpClient())
                    .baseUrl("https://test.dengi-credit.org")
                    .addConverterFactory(MoshiConverterFactory.create(moshi.build()))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
                api = retrofit.create(Api::class.java)
            }
            return api!!
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }.build()
        }


    }

}