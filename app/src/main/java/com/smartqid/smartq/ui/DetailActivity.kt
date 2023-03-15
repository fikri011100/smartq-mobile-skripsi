package com.smartqid.smartq.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.ActivityDetailBinding
import com.smartqid.smartq.revamp.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    lateinit var sharedPreferences: SharedPreferences

    fun getData(): String? {
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val data = sharedPreferences.getString("Kategori","")
        return data
    }

    override val bindingInflater: (LayoutInflater) -> ActivityDetailBinding
        get() = ActivityDetailBinding::inflate

    override fun initView() {

    }


}