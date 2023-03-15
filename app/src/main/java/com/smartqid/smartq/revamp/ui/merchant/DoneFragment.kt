package com.smartqid.smartq.revamp.ui.merchant

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.smartqid.smartq.databinding.FragmentDoneBinding
import com.smartqid.smartq.revamp.common.BaseFragment
import com.smartqid.smartq.revamp.ui.home.HomeActivity

class DoneFragment : BaseFragment<FragmentDoneBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoneBinding
        get() = FragmentDoneBinding::inflate

    override fun initObservable() {
    }

    override fun initView() {
        binding.buttonDone.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context?.startActivity(intent)
            activity?.finishAffinity()
        }
    }

}