package com.nadikarim.androidsubmission3.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserEntity(
    @field:ColumnInfo(name = "username")
    val username: String,

    @field:PrimaryKey
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
)
