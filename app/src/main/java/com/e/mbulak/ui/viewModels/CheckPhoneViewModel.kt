package com.e.mbulak.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.e.mbulak.App
import com.e.mbulak.data.model.ApiResult
import com.e.mbulak.utils.Resource
import kotlinx.coroutines.*
import java.io.IOException

class CheckPhoneViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val checkUserPhone = MutableLiveData<ApiResult>()

    fun checkPhoneNumber(phoneNumber : String) {

        Log.d("ololo", "PHONE NUMBER " + phoneNumber)
        viewModelScope.launch {
            try {
                Log.d("ololo", "CoroutineScope")
                val response = App.repository.checkPhoneNumber(phoneNumber)
                Log.d("ololo", "CoroutineScope 2")
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d("ololo", "isSuccessful")
                        var id  = response.body()!!.result?.id
                        var code = response.body()!!.code
                        Log.d("ololo", "isSuccessful " + id)
                        Log.d("ololo", "isSuccessful code" + code)
                        Log.d("ololo", "isSuccessful error" + response.body()!!.error?.message)
                        checkUserPhone.postValue(response.body())


                    } else {
                        Log.d("ololo", "not isSuccessful")
                        onError("Error : ${response.message()} ")
                    }
                }
            } catch (e : IOException) {
                onError(e.message!!)
            }
        }

    }

    fun getAvailableCountries() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(App.repository.listAvailableCountry(1)))
        } catch (e : IOException) {
            emit(Resource.error(data = null, message = e.message ?: "Error occurred"))
        }

    }

    private fun onError(message: String) {
        Log.d("ololo", "Error " + message)
        errorMessage.postValue(message)
        loading.postValue(false)
    }


}