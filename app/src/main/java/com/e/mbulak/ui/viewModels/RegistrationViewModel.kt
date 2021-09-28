package com.e.mbulak.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.mbulak.App
import com.e.mbulak.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class RegistrationViewModel : ViewModel() {


    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val userRegistration = MutableLiveData<RegistrationModel>()
    val questions = MutableLiveData<SecretQuestions>()
    val gender = MutableLiveData<Genders>()
    val nationality = MutableLiveData<Countries>()
    val trafficSource = MutableLiveData<TrafficSources>()


    fun registration(newUserRegistration : NewUserRegistrationModel) {

        Log.d("ololo", "new User registration " + newUserRegistration.toString())

        loading.postValue(true)
        viewModelScope.launch {
            try {
                Log.d("ololo", "CoroutineScope")
                val response = App.repository.registration(newUserRegistration)
                Log.d("ololo", "CoroutineScope 2")
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        userRegistration.postValue(response.body())
                        loading.postValue(true)
                        Log.d("ololo", response.body()?.code.toString())
                        Log.d("ololo", "code "  + response.body()?.error?.code)
                        Log.d("ololo", "id "  + response.body()?.result?.id)
                        response.body()?.error?.message?.let { Log.d("ololo", "error = " + it) }
                        response.body()?.result?.login?.let { Log.d("ololo", it) }
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

    fun getNeededData() {
        loading.postValue(true)
        viewModelScope.launch {
            try {
                Log.d("ololo", "CoroutineScope")
                val responseSecretQuestions = App.repository.listSecretQuestion(1)
                val responceGender = App.repository.listGender(1)
                val responseNationality = App.repository.listAvailableCountry(1)
                val responseTrafficSource = App.repository.listTrafficSource(1)

                Log.d("ololo", "CoroutineScope 2")
                withContext(Dispatchers.IO) {
                    if (responseSecretQuestions.isSuccessful && responceGender.isSuccessful &&
                            responseNationality.isSuccessful && responseTrafficSource.isSuccessful) {

                        questions.postValue(responseSecretQuestions.body())
                        gender.postValue(responceGender.body())
                        nationality.postValue(responseNationality.body())
                        trafficSource.postValue(responseTrafficSource.body())
                        loading.postValue(false)
                    } else {
                        Log.d("ololo", "not isSuccessful")
                        onError("Error : ${responseSecretQuestions.message()} ")
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