package com.nadikarim.androidsubmission3.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nadikarim.androidsubmission3.data.local.entity.UserEntity
import com.nadikarim.androidsubmission3.data.local.room.UserDao
import com.nadikarim.androidsubmission3.data.local.room.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: UserDao?
    private var userDatabase: UserDatabase? = UserDatabase.getInstance(application)

    init {
        userDao = userDatabase?.userDao()
    }

    fun readAllData(): LiveData<List<UserEntity>>? {
        return userDao?.readAllData()
    }

}