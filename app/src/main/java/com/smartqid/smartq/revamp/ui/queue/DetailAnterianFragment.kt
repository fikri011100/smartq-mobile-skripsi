package com.smartqid.smartq.revamp.ui.queue

import android.view.LayoutInflater
import android.view.ViewGroup
import com.smartqid.smartq.databinding.FragmentDetailAnterianBinding
import com.smartqid.smartq.revamp.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAnterianFragment : BaseFragment<FragmentDetailAnterianBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailAnterianBinding
        get() = FragmentDetailAnterianBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {

    }
}