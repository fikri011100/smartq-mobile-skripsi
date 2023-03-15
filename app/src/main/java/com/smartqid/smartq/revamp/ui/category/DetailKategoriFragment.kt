package com.smartqid.smartq.revamp.ui.category

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.databinding.FragmentDetailKategoriBinding
import com.smartqid.smartq.revamp.common.BaseFragment
import com.smartqid.smartq.revamp.domain.model.Store
import com.smartqid.smartq.ui.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailKategoriFragment : BaseFragment<FragmentDetailKategoriBinding>() {

    private lateinit var ref: DatabaseReference
    lateinit var sharedPreferences: SharedPreferences

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailKategoriBinding
        get() = FragmentDetailKategoriBinding::inflate

    override fun initObservable() {
    }

    override fun initView() {
        ref = Firebase.database.reference
        val viewModel = ViewModelProvider(this)[KategoriViewModel::class.java]
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        sharedPreferences = context?.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)!!
        val bundle = sharedPreferences.getString("Kategori", "")
        binding.textBarMenu.text = bundle
        viewModel.getToko(bundle!!).observe(this) {
            if (it.products != null) {
                val adapterKat = AdapterKategori(requireContext(), it.products!!)
                binding.recyclerviewKategori.apply {
                    adapter = adapterKat
                    layoutManager = LinearLayoutManager(requireContext())
                }
                binding.pgKategori.visibility = View.GONE
                binding.recyclerviewKategori.visibility = View.VISIBLE
                binding.imageEmptyKategori.visibility = View.GONE
            } else {
                binding.recyclerviewKategori.visibility = View.GONE
                binding.imageEmptyKategori.visibility = View.VISIBLE
                binding.pgKategori.visibility = View.GONE
            }
        }
    }
}