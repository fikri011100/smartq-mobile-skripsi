package com.smartqid.smartq.revamp.ui.auth.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentSecondIntroBinding
import com.smartqid.smartq.revamp.common.BaseFragment

class SecondIntroFragment : BaseFragment<FragmentSecondIntroBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSecondIntroBinding
        get() = FragmentSecondIntroBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        binding.floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_secondIntroFragment_to_startedFragment)
        }
    }

}