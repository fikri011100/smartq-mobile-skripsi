package com.smartqid.smartq.revamp.ui.home.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import com.smartqid.smartq.databinding.FragmentVoucherBinding
import com.smartqid.smartq.revamp.common.BaseFragment
import com.smartqid.smartq.revamp.domain.model.Favorite
import com.smartqid.smartq.revamp.domain.model.Store

class VoucherFragment : BaseFragment<FragmentVoucherBinding>() {

    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVoucherBinding
        get() = FragmentVoucherBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        auth = FirebaseAuth.getInstance()
        ref = Firebase.database.reference
        val viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        var list: ArrayList<Favorite>?
        list = arrayListOf()
        viewModel.getResponseUsingLiveData().observe(this) {
            try {
                val favoriteAdapter =  FavoriteAdapter(requireContext(), it.products!!)
                binding.listToko.apply {
                    adapter = favoriteAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

}