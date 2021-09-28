package com.e.mbulak.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.mbulak.App
import com.e.mbulak.data.model.ApiResult
import kotlinx.coroutines.*
import java.io.IOException

class RegistrationPinViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val success = MutableLiveData<ApiResult>()

    fun checkCode(id : Int, code : Int) {

        viewModelScope.launch {
            try {
                Log.d("ololo", "CoroutineScope")
                val response = App.repository.checkCode(id, code)
                Log.d("ololo", "CoroutineScope 2")
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d("ololo", " message = " + response.body()?.result?.message)
                        success.postValue(response.body())
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

    private fun onError(message: String) {
        Log.d("ololo", "Error " + message)
        errorMessage.postValue(message)
        loading.postValue(false)
    }

}