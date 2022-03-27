package com.nadikarim.androidsubmission3.ui.detail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nadikarim.androidsubmission3.data.Repository
import com.nadikarim.androidsubmission3.data.local.entity.UserEntity
import com.nadikarim.androidsubmission3.data.local.room.UserDao
import com.nadikarim.androidsubmission3.data.local.room.UserDatabase
import com.nadikarim.androidsubmission3.data.remote.response.DetailResponse
import com.nadikarim.androidsubmission3.util.Constants.RETROFIT_FAIL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private val _detailUser: MutableLiveData<DetailResponse?> = MutableLiveData()
    val detailUser: LiveData<DetailResponse?> = _detailUser

    private var repository: Repository = Repository()
    private var userDao: UserDao?
    private var userDatabase: UserDatabase? = UserDatabase.getInstance(application)

    init {
        userDao = userDatabase?.userDao()
    }

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            val response = repository.getDetailUser(username)
            _detailUser.postValue(response)
        }
    }

    fun addUser(username: String, id: Int, avatarUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = UserEntity(
                username,
                id,
                avatarUrl
            )
            userDao?.insertUser(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeUser(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao?.deleteUser(id)
        }
    }

}