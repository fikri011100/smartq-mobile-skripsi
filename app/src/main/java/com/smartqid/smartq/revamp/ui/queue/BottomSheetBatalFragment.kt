package com.smartqid.smartq.revamp.ui.queue

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentBottomSheetBatalBinding
import com.smartqid.smartq.revamp.common.BaseBottomSheetFragment
import com.smartqid.smartq.revamp.ui.home.HomeActivity

class BottomSheetBatalFragment(
    val uid: String,
    val email: String,
    val nama: String,
    val namaToko: String,
    val noTelp: String,
    val status: String,
    val uidToko: String,
    val urutan: String,
    val waktuDibuat: String,
    val waktuDipanggil: String
) : BaseBottomSheetFragment<FragmentBottomSheetBatalBinding>() {

    private lateinit var answer: String
    private lateinit var ref: DatabaseReference

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBottomSheetBatalBinding
        get() = FragmentBottomSheetBatalBinding::inflate

    override fun initObservable() {
    }

    override fun initView() {
        ref = Firebase.database.reference
        binding.rgBatal.setOnCheckedChangeListener { group, checkedId ->
            var rb = view?.findViewById<RadioButton>(checkedId)
            if (rb != null) {
                when (rb.text) {
                    getString(R.string.batal_option1) -> {
                        binding.alasan.visibility = View.GONE
                        answer = rb.text.toString()
                    }
                    getString(R.string.batal_option2) -> {
                        binding.alasan.visibility = View.GONE
                        answer = rb.text.toString()
                    }
                    getString(R.string.batal_option3) -> {
                        binding.alasan.visibility = View.VISIBLE
                        answer = binding.textBatalMenu.text.toString()
                    }
                }
                binding.buttonBatal.setOnClickListener {
                    ref.child("anterian")
                    ref = FirebaseDatabase.getInstance().getReference("anterian")
                    ref.child(uid).child("status").setValue("Menunggu Konfirmasi Batal")
                    saveFirebase(answer)
                    Toast.makeText(
                        requireContext(),
                        "Pesanan Berhasil Dibatalkan!",
                        Toast.LENGTH_LONG
                    ).show()
                    requireActivity().startActivity(
                        Intent(
                            requireContext(),
                            HomeActivity::class.java
                        )
                    )
                    requireActivity().finishAffinity()
                }
            }
        }
    }

    private fun saveFirebase(alasan: String) {
        val database = Firebase.database
        val myRef = database.getReference("anterian/$uid")
        var map = HashMap<String, Any>()
        map.put("email", email)
        map.put("nama", nama)
        map.put("namaToko", namaToko)
        map.put("noTelp", noTelp)
        map.put("status", "Dibatalkan")
        map.put("alasan", alasan)
        map.put("uid", uidToko)
        map.put("urutan", urutan)
        map.put("waktuDibuat", waktuDibuat)
        map.put("waktuDipanggil", waktuDipanggil)
        myRef.setValue(map)
    }
}