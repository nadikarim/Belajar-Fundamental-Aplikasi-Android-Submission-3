package com.nadikarim.androidsubmission3.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nadikarim.androidsubmission3.ui.detail.follow.FollowersFragment
import com.nadikarim.androidsubmission3.ui.detail.follow.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, data: Bundle): FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle = data

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = fragmentBundle
        return fragment as Fragment
    }
}