package com.nadikarim.androidsubmission3.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadikarim.androidsubmission3.R
import com.nadikarim.androidsubmission3.data.local.entity.UserEntity
import com.nadikarim.androidsubmission3.data.remote.response.User
import com.nadikarim.androidsubmission3.databinding.ActivityFavoriteBinding
import com.nadikarim.androidsubmission3.ui.detail.DetailActivity
import com.nadikarim.androidsubmission3.data.ListAdapter
import com.nadikarim.androidsubmission3.util.Constants

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite)

        adapter = ListAdapter()
        setRecyclerView()

        viewModel.readAllData()?.observe(this){
            val list = mapList(it)
            if (it != null) {
                adapter.setUser(list)
            }
        }
    }

    private fun mapList(users: List<UserEntity>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users)  {
            val userMapped = User(
                user.id,
                user.username,
                user.avatarUrl
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    private fun setRecyclerView() {
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: User) {
                    val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USERNAME, data.login)
                    intent.putExtra(Constants.EXTRA_ID, data.id)
                    startActivity(intent)
                    finish()
                }

            })
        }
    }
}