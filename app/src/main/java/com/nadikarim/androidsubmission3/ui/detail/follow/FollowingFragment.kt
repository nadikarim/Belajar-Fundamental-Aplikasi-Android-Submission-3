package com.nadikarim.androidsubmission3.ui.detail.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadikarim.androidsubmission3.R
import com.nadikarim.androidsubmission3.data.Repository
import com.nadikarim.androidsubmission3.data.remote.response.User
import com.nadikarim.androidsubmission3.databinding.FragmentFollowBinding
import com.nadikarim.androidsubmission3.ui.detail.DetailActivity
import com.nadikarim.androidsubmission3.data.ListAdapter
import com.nadikarim.androidsubmission3.util.Constants.EXTRA_AVATAR
import com.nadikarim.androidsubmission3.util.Constants.EXTRA_ID
import com.nadikarim.androidsubmission3.util.Constants.EXTRA_USERNAME

class FollowingFragment: Fragment(R.layout.fragment_follow) {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: FollowViewModel
    private lateinit var username: String
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        username = arguments?.getString(EXTRA_USERNAME).toString()

        binding = FragmentFollowBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.VISIBLE
        setRecyclerView()

        val repository = Repository()
        val viewModelFactory = FollowViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FollowViewModel::class.java]

        viewModel.getDetailFollowing(username)
        viewModel.listFollowing.observe(viewLifecycleOwner) {response ->
            if (response != null) {
                adapter.setUser(response)
                binding.progressBar.visibility = View.GONE
            }
        }

        return binding.root
    }

    private fun setRecyclerView() {
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(activity)
        adapter = ListAdapter()
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, data.login)
                intent.putExtra(EXTRA_AVATAR, data.avatarUrl)
                intent.putExtra(EXTRA_ID, data.id)
                startActivity(intent)
            }

        })
    }
}