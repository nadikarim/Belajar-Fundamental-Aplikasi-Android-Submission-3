package com.nadikarim.androidsubmission3.data.remote.retrofit

import com.nadikarim.androidsubmission3.data.remote.response.DetailResponse
import com.nadikarim.androidsubmission3.data.remote.response.User
import com.nadikarim.androidsubmission3.util.ResponseStatus
import retrofit2.Response

class ApiClient(private val apiService: ApiService) {

    suspend fun getUsers(): ResponseStatus<ArrayList<User>> {
        return safeApiCall { apiService.getListUsers() }
    }

    suspend fun getDetailUser(username: String): ResponseStatus<DetailResponse> {
        return safeApiCall { apiService.getDetailUser(username) }
    }

    suspend fun getDetailFollowers(username: String): ResponseStatus<ArrayList<User>> {
        return safeApiCall { apiService.getDetailFollowers(username) }
    }

    suspend fun getDetailFollowing(username: String): ResponseStatus<ArrayList<User>> {
        return safeApiCall { apiService.getDetailFollowing(username) }
    }

    private inline fun <T> safeApiCall(apICall: () -> Response<T>): ResponseStatus<T> {
        return try {
            ResponseStatus.success(apICall.invoke())
        }catch (e: Exception) {
            ResponseStatus.failure(e)
        }
    }

}