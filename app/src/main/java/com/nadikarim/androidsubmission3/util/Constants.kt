package com.nadikarim.androidsubmission3.util

import androidx.annotation.StringRes
import com.nadikarim.androidsubmission3.R

object Constants {
    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.tab_followers,
        R.string.tab_following
    )

    const val BASE_URL = "https://api.github.com/"
    const val TOKEN = "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"
    const val EXTRA_USERNAME = "extra_username"
    const val EXTRA_ID = "extra_id"
    const val EXTRA_AVATAR = "extra_avatar"
    const val RETROFIT_FAIL = "Retrofit Fail"
}