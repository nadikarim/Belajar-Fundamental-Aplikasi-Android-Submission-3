package com.nadikarim.androidsubmission3.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.nadikarim.androidsubmission3.R
import com.nadikarim.androidsubmission3.data.remote.response.DetailResponse
import com.nadikarim.androidsubmission3.databinding.ActivityDetailBinding
import com.nadikarim.androidsubmission3.ui.favorite.FavoriteActivity
import com.nadikarim.androidsubmission3.ui.setting.SettingActivity
import com.nadikarim.androidsubmission3.util.Constants.EXTRA_AVATAR
import com.nadikarim.androidsubmission3.util.Constants.EXTRA_ID
import com.nadikarim.androidsubmission3.util.Constants.EXTRA_USERNAME
import com.nadikarim.androidsubmission3.util.Constants.TAB_TITLES
import com.uk.tastytoasty.TastyToasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)

        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME ,username)

        username?.let { viewModel.getDetailUser(it) }
        viewModel.detailUser.observe(this) { detailResponse ->
            if(detailResponse != null) {
                setDetailUser(detailResponse)
            }
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    isChecked = if (count > 0) {
                        favorited(true)
                        true
                    } else {
                        favorited(false)
                        false
                    }
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                if (username != null) {
                    avatar?.let { avatar -> viewModel.addUser(username, id, avatar) }
                    favorited(false)
                    toastAndFinish("$username telah ditambahkan ke daftar favorite")
                }
            } else {
                viewModel.removeUser(id)
                favorited(true)
                toastAndFinish("$username telah dihapus dari daftar davorite")
            }
        }
        setViewPager(bundle)
    }

    private fun setDetailUser(response: DetailResponse) {
        binding.apply {
            tvFollowers.text = response.followers.toString()
            tvFollowing.text = response.following.toString()
            tvName.text = response.name
            tvUsername.text = getString(R.string.add_username, response.login)
            tvRepository.text = response.publicRepos.toString()
            Glide.with(this@DetailActivity)
                .load(response.avatarUrl)
                .into(imgAvatar)
        }
    }

    private fun toastAndFinish(message: String) {
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        TastyToasty.star(this@DetailActivity, message).show()
        startActivity(Intent(this, FavoriteActivity::class.java))
        finish()
    }

    private fun favorited(state: Boolean)  {
        if (state) {
            binding.floatingActionButton.setImageDrawable(
                ContextCompat.getDrawable(applicationContext,
                    R.drawable.ic_favorite_fill
                )
            )
        } else {
            binding.floatingActionButton.setImageDrawable(
                ContextCompat.getDrawable(applicationContext,
                    R.drawable.ic_favorite_border)
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
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

    private fun setViewPager(bundle: Bundle) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}