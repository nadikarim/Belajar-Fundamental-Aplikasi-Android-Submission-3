package com.nadikarim.androidsubmission3.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nadikarim.androidsubmission3.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user ORDER BY username ASC")
    fun readAllData(): LiveData<List<UserEntity>>

    @Query("SELECT count(*) FROM user WHERE user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: Int): Int
}