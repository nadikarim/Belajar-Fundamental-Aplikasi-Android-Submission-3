package com.nadikarim.androidsubmission3.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadikarim.androidsubmission3.R
import com.nadikarim.androidsubmission3.data.ListAdapter
import com.nadikarim.androidsubmission3.data.Repository
import com.nadikarim.androidsubmission3.data.remote.response.User
import com.nadikarim.androidsubmission3.databinding.ActivityMainBinding
import com.nadikarim.androidsubmission3.ui.detail.DetailActivity
import com.nadikarim.androidsubmission3.ui.favorite.FavoriteActivity
import com.nadikarim.androidsubmission3.ui.setting.SettingActivity
import com.nadikarim.androidsubmission3.util.Constants.EXTRA_AVATAR
import com.nadikarim.androidsubmission3.util.Constants.EXTRA_ID
import com.nadikarim.androidsubmission3.util.Constants.EXTRA_USERNAME

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListAdapter()
        setRecyclerView()


        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.progressBar.visibility = View.VISIBLE
        viewModel.getListUser()
        setList()

        binding.apply {

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {

                    viewModel.searchUser(query)
                    setListUser()
                    searchView.clearFocus()
                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean {

                    return false
                }
            })
        }

    }

    private fun setRecyclerView() {
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: User) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(EXTRA_USERNAME, data.login)
                    intent.putExtra(EXTRA_ID, data.id)
                    intent.putExtra(EXTRA_AVATAR, data.avatarUrl)
                    startActivity(intent)
                }

            })
        }
    }

    private fun setList() {
        viewModel.userList.observe(this) {response ->
            if (response != null) {
                adapter.setUser(response)
            }
            binding.progressBar.visibility = View.INVISIBLE
        }

    }

    private fun setListUser() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.listUserSearch.observe(this) {
            if (it != null) {
                adapter.setUser(it)
            }
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java) )
                true
            }
            R.id.menu_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }
}