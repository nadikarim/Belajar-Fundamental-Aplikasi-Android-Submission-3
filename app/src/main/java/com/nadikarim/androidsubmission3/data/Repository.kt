package com.nadikarim.androidsubmission3.data

import com.nadikarim.androidsubmission3.data.remote.response.DetailResponse
import com.nadikarim.androidsubmission3.data.remote.response.User
import com.nadikarim.androidsubmission3.data.remote.retrofit.ApiInstance
import retrofit2.Call
import retrofit2.Response

class Repository {

    suspend fun getListUsers(): ArrayList<User>? {
        val request = ApiInstance.apiClient.getUsers()

        if (request.failed) return null

        if (!request.isSuccessful) return null

        return request.body

    }

    suspend fun getDetailUser(username: String): DetailResponse? {
        val request = ApiInstance.apiClient.getDetailUser(username)

        if (request.failed) return null

        if (!request.isSuccessful) return null

        return request.body

    }


    suspend fun getDetailFollowers(username: String): ArrayList<User>? {
        val request = ApiInstance.apiClient.getDetailFollowers(username)

        if (request.failed) return null

        if (!request.isSuccessful) return null

        return request.body
    }


    suspend fun getDetailFollowing(username: String): ArrayList<User>? {
        val request = ApiInstance.apiClient.getDetailFollowing(username)

        if (request.failed) return null

        if (!request.isSuccessful) return null

        return request.body
    }
}