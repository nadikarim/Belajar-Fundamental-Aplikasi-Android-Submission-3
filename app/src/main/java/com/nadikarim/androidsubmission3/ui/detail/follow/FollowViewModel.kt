package com.nadikarim.androidsubmission3.ui.detail.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadikarim.androidsubmission3.data.Repository
import com.nadikarim.androidsubmission3.data.remote.response.User
import kotlinx.coroutines.launch

class FollowViewModel(private val repository: Repository): ViewModel() {
    private val _listFollowers: MutableLiveData<ArrayList<User>?> = MutableLiveData()
    val listFollowers: LiveData<ArrayList<User>?> = _listFollowers

    private val _listFollowing: MutableLiveData<ArrayList<User>?> = MutableLiveData()
    val listFollowing: LiveData<ArrayList<User>?> = _listFollowing

    fun getDetailFollowers(username: String) {
        viewModelScope.launch {
            val response = repository.getDetailFollowers(username)
            _listFollowers.value = response
        }
    }

    fun getDetailFollowing(username: String) {
        viewModelScope.launch {
            val response = repository.getDetailFollowing(username)
            _listFollowing.value = response
        }
    }

}