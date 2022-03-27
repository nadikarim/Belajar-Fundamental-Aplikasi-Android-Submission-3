package com.nadikarim.androidsubmission3.data.remote.retrofit


import com.nadikarim.androidsubmission3.data.remote.response.DetailResponse
import com.nadikarim.androidsubmission3.data.remote.response.SearchResponse
import com.nadikarim.androidsubmission3.data.remote.response.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getListUsers(): Response<ArrayList<User>>

    @GET("search/users")
    fun getUserSearch(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username")username : String
    ): Response<DetailResponse>

    @GET("users/{username}/followers")
    suspend fun getDetailFollowers(
        @Path("username")username : String
    ): Response<ArrayList<User>>

    @GET("users/{username}/following")
    suspend fun getDetailFollowing(
        @Path("username")username : String
    ): Response<ArrayList<User>>
}