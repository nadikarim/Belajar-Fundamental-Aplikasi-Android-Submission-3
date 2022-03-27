package com.nadikarim.androidsubmission3.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadikarim.androidsubmission3.data.Repository
import com.nadikarim.androidsubmission3.data.remote.retrofit.ApiInstance
import com.nadikarim.androidsubmission3.data.remote.response.SearchResponse
import com.nadikarim.androidsubmission3.data.remote.response.User
import com.nadikarim.androidsubmission3.util.Constants.RETROFIT_FAIL
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    private val _listUserSearch = MutableLiveData<ArrayList<User>>()
    val listUserSearch: LiveData<ArrayList<User>> = _listUserSearch

    private val _userList: MutableLiveData<ArrayList<User>?> = MutableLiveData()
    val userList: LiveData<ArrayList<User>?> = _userList


    fun searchUser(query: String) {
        ApiInstance.api.getUserSearch(query)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        _listUserSearch.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.d(RETROFIT_FAIL, t.message.toString())
                }

            })
    }

    fun getListUser() {
        viewModelScope.launch {
            val response = repository.getListUsers()
            _userList.postValue(response)
        }
    }

}