package com.smartqid.smartq.revamp.ui.home.notification

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartqid.smartq.databinding.ActivityNotificationBinding
import com.smartqid.smartq.revamp.common.BaseActivity
import com.smartqid.smartq.revamp.domain.model.Notification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityNotificationBinding
        get() = ActivityNotificationBinding::inflate
    private var array: ArrayList<Notification>? = null

    override fun initView() {
        array = arrayListOf()
        initData()
        val adapterNotif = AdapterNotification(applicationContext, array!!)
        binding.rvNotification.apply {
            adapter = adapterNotif
            layoutManager = LinearLayoutManager(applicationContext)
        }
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initData() {
        array?.add(
            Notification(
                "Panggilan untuk Magueyer",
                "Sudah waktunya antrianmu",
                "13:30",
                true
            )
        )
        array?.add(
            Notification(
                "Panggilan untuk Argus",
                "Sudah waktunya antrianmu",
                "13:30",
                false
            )
        )
    }

}