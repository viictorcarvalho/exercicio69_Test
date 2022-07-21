package br.com.zup.movieflix.login.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.zup.movieflix.PREFERENCE_KEY
import br.com.zup.movieflix.SAVE_USER_PASS_FLAG_KEY
import br.com.zup.movieflix.USER_NAME_LOGIN_KEY
import br.com.zup.movieflix.USER_PASSWORD_LOGIN_KEY
import br.com.zup.movieflix.login.model.LoginModel
import br.com.zup.movieflix.login.repository.LoginRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LoginRepository()
    private var _response: MutableLiveData<LoginModel> = MutableLiveData()
    val response: LiveData<LoginModel> = _response

    private val _savedData : MutableLiveData<LoginModel> = MutableLiveData()
    val savedData : LiveData<LoginModel> = _savedData

    private  val _saveDataFlag : MutableLiveData<Boolean> = MutableLiveData()
    val saveDataFlag : LiveData<Boolean> = _saveDataFlag


    private val pref = application.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)
    private val prefEditor = pref.edit()

    fun getSavedData(){
        try {
            val user = pref.getString(USER_NAME_LOGIN_KEY, "").toString()
            val password = pref.getString(USER_PASSWORD_LOGIN_KEY, "").toString()
            val savedUser = LoginModel(user, password)
            _savedData.value = savedUser
            _saveDataFlag.value = pref.getBoolean(SAVE_USER_PASS_FLAG_KEY, false)
        }catch (e:Exception){
            Log.i("Error", "------> ${e.message}")
        }
    }

    fun authentication (login : LoginModel, flagSaveData:Boolean){
        try {
            prefEditor.putBoolean(SAVE_USER_PASS_FLAG_KEY, flagSaveData)
            if(flagSaveData){
                prefEditor.putString(USER_NAME_LOGIN_KEY, login.user)
                prefEditor.putString(USER_PASSWORD_LOGIN_KEY, login.password)
                prefEditor.apply()
            }else{
                prefEditor.remove(USER_NAME_LOGIN_KEY)
                prefEditor.remove(USER_PASSWORD_LOGIN_KEY)
                prefEditor.apply()
            }
            _response.value = repository.authenticate(login)
        }catch (ex: Exception){
            Log.i("Error", "------> ${ex.message}")
        }

    }
}