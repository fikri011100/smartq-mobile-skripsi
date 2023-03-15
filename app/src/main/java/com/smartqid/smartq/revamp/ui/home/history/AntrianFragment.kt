package com.smartqid.smartq.revamp.ui.home.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.databinding.FragmentAntrianBinding
import com.smartqid.smartq.revamp.common.BaseFragment

class AntrianFragment : BaseFragment<FragmentAntrianBinding>() {

    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: HistoryViewModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAntrianBinding
        get() = FragmentAntrianBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        auth = FirebaseAuth.getInstance()
        ref = Firebase.database.reference
        binding.tabsTenant.addTab(binding.tabsTenant.newTab().setText("Sedang Antri"))
        binding.tabsTenant.addTab(binding.tabsTenant.newTab().setText("Selesai"))
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        dataAntrian()

        binding.tabsTenant.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.contentDescription.toString()) {
                    "Sedang Antri" ->
                        dataAntrian()
                    "Selesai" ->
                        dataRiwayat()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    fun dataAntrian() {
        viewModel.getAntrianLiveData().observe(this) {
            if (it.products != null) {
                try {
                    ref = FirebaseDatabase.getInstance().getReference("anterian")
                    val riwayatAdapter = AntrianAdapter(it.products!!.reversed(), requireContext(), ref, it.listUid!!)
                    binding.dataRiwayat.apply {
                        adapter = riwayatAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                    binding.pgRiwayat.visibility = View.GONE
                    binding.dataRiwayat.visibility = View.VISIBLE
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            } else {
                binding.pgRiwayat.visibility = View.GONE
                binding.imageEmptyRiwayat.visibility = View.VISIBLE
                binding.textNoData.visibility = View.VISIBLE
            }
        }
    }

    fun dataRiwayat() {
        ref = FirebaseDatabase.getInstance().getReference("anterian")
        viewModel.getHistoryLiveData().observe(this) {
            if (it.products != null) {
                try {
                    val riwayatAdapter = RiwayatAdapter(it.products!!.reversed(), requireContext(), ref)
                    binding.dataRiwayat.apply {
                        adapter = riwayatAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                    binding.pgRiwayat.visibility = View.GONE
                    binding.dataRiwayat.visibility = View.VISIBLE
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            } else {
                binding.pgRiwayat.visibility = View.GONE
                binding.imageEmptyRiwayat.visibility = View.VISIBLE
                binding.textNoData.visibility = View.VISIBLE
            }
        }
    }
}
