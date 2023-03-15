package com.smartqid.smartq.ui.home.riwayat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentRiwayatIsiBinding
import com.smartqid.smartq.revamp.domain.model.Transaction
import com.smartqid.smartq.revamp.ui.home.history.AntrianAdapter
import com.smartqid.smartq.revamp.ui.home.history.RiwayatAdapter
import timber.log.Timber

class RiwayatObjectFragment : Fragment(R.layout.fragment_riwayat_isi) {

    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentRiwayatIsiBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRiwayatIsiBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        ref = Firebase.database.reference
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            if (this.getInt("object") == 1) {
                Timber.d( this.getInt("object").toString())
                dataAntrian()
            } else {
                Timber.d( this.getInt("object").toString())
                dataRiwayat()
            }
        }
    }

    fun dataAntrian() {
        var list: ArrayList<Transaction>?
        list = arrayListOf()
        var listUID: ArrayList<String> = arrayListOf()
        ref = FirebaseDatabase.getInstance().getReference("anterian")
        var query: Query = ref.orderByChild("email").equalTo(auth.currentUser.email).apply {
            addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        list = ArrayList()
                        for (h in snapshot.children) {
                            if (h.child("status").value.toString().equals("Diterima")) {
                                val item = h.getValue(Transaction::class.java)
                                list?.add(item!!)
                                listUID.add(h.key.toString())
                            }
                        }

                        try {
                            val riwayatAdapter = AntrianAdapter(list!!, requireContext(), ref, listUID)
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

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }

    fun dataRiwayat() {
        var list: ArrayList<Transaction>?
        list = arrayListOf()
        ref = FirebaseDatabase.getInstance().getReference("anterian")
        val query: Query = ref.orderByChild("email").equalTo(auth.currentUser.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    list = ArrayList()
                    for (h in snapshot.children) {
                        if (h.child("status").value.toString().equals("Selesai") || h.child("status").value.toString().equals("Dibatalkan")) {
                            val item = h.getValue(Transaction::class.java)
                            list?.add(item!!)
                        }
                    }

                    try {
                        val riwayatAdapter = RiwayatAdapter(list!!, requireContext(), ref)
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

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}