package com.smartqid.smartq.revamp.ui.auth.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentIntroBinding
import com.smartqid.smartq.revamp.common.BaseFragment

class IntroFragment : BaseFragment<FragmentIntroBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIntroBinding
        get() = FragmentIntroBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        binding.floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_secondIntroFragment)
        }
    }

}