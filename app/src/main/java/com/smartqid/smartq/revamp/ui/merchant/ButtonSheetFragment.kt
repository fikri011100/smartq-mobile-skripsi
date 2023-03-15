package com.smartqid.smartq.revamp.ui.merchant

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentButtonSheetBinding
import com.smartqid.smartq.revamp.common.BaseBottomSheetFragment
import com.smartqid.smartq.revamp.ui.queue.DetailAntrianViewModel
import timber.log.Timber


class ButtonSheetFragment(
    val email: String,
    val vendor: String,
    val waktuDipanggil: String,
    val waktuDibuat: String,
    val uid: String,
    val waktubuka: String,
    val aktif: String,
    val estimasi: String
) : BaseBottomSheetFragment<FragmentButtonSheetBinding>() {

    private lateinit var ref: DatabaseReference
    lateinit var setPreferences: SharedPreferences

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentButtonSheetBinding
        get() = FragmentButtonSheetBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        setPreferences = requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        ref = Firebase.database.reference
        val viewModel = ViewModelProvider(this)[DetailAntrianViewModel::class.java]
        Logger.d("data",uid)
        binding.textWaktuAntrian.text = convertMinutes((setPreferences.getInt("urutan", 0) * estimasi.toInt()))
        binding.textNomor.text = setPreferences.getInt("urutan", 0).toString()
        binding.textToko.text = vendor
        binding.textToko.isSelected = true
        Logger.d(estimasi)
        Timber.d(setPreferences.getString("name", "") + setPreferences.getString("telephone", ""))
        binding.buttonMulaiAntri.setOnClickListener {
            viewModel.addAntrian(
                email,
                setPreferences.getString("name", "")!!,
                vendor,
                setPreferences.getString("telephone", "")!!,
                uid,
                waktuDibuat,
                (setPreferences.getInt("urutan", 0) * estimasi.toInt()).toString()
            )

            val Editor: SharedPreferences.Editor = setPreferences.edit()
            Editor.putInt("banner", 1)
            Editor.remove("name")
            Editor.remove("urutan")
            Editor.remove("telephone")
            Editor.apply()

            requireParentFragment().findNavController().navigate(R.id.action_tokoFragment_to_doneAntrianFragment)
        }
    }

    fun convertMinutes(minutes: Int) : String {
        if (minutes > 60) {
            val h = minutes / 60
            val m = minutes % 60

            return String.format("%01d:%02d",h,m)
        } else {
            return minutes.toString()
        }
    }

}