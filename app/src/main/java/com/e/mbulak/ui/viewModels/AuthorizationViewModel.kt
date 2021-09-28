package com.e.mbulak.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.e.mbulak.App
import com.e.mbulak.BuildConfig
import com.e.mbulak.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AuthorizationViewModel : ViewModel() {


    fun login(login : String, password : String, uid : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = App.repository.login(login = login, password = password, uid = uid, appid = BuildConfig.APPLICATION_ID,
                system = 2)))
            /*val response = App.repository.login(login = login, password = password, uid = uid, appid =  BuildConfig.APPLICATION_ID,
                system = 2)*/



            /*if(response.isSuccessful) {
                Log.d("ololo", "is Success login" + response.body()?.result?.login)
            } else {
                Log.d("ololo", "is Success code" + response.body()?.code)
            }*/

        } catch (e : Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error occurred"))
        }



    }


}