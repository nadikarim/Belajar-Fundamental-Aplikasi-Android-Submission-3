package com.nadikarim.androidsubmission3.ui.detail.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadikarim.androidsubmission3.data.Repository

class FollowViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FollowViewModel(repository) as T
    }
}