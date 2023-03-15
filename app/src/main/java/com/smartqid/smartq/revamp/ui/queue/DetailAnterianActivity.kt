package com.smartqid.smartq.revamp.ui.queue

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.databinding.ActivityDetailAnterianBinding
import com.smartqid.smartq.revamp.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAnterianActivity : BaseActivity<ActivityDetailAnterianBinding>() {

    private lateinit var ref: DatabaseReference
    private lateinit var uid: String
    private lateinit var urutan: String
    private lateinit var idAnterian: String

    override val bindingInflater: (LayoutInflater) -> ActivityDetailAnterianBinding
        get() = ActivityDetailAnterianBinding::inflate

    override fun initView() {
        ref = Firebase.database.reference
        uid = intent.getStringExtra("idtoko").toString()
        urutan = intent.getStringExtra("urutan").toString()
        idAnterian = intent.getStringExtra("idAnterian").toString()
        binding.textNoAntrian.text = urutan
        var bottomSheet: BottomSheetBatalFragment

        ref = FirebaseDatabase.getInstance().getReference("toko")
        ref.child(uid).get().addOnSuccessListener {
            Glide.with(applicationContext).load(it.child("gambar").value.toString())
                .into(binding.imgToko)
            binding.textNamaToko.text = it.child("namaToko").value.toString()
            binding.detailKota.text = "${it.child("alamat").value.toString()}, ${it.child("kota").value.toString()}"
            binding.detailData.text = "${it.child("hariAktif").value.toString()}, ${it.child("waktuBuka").value.toString()}"
//            binding.detailAktif.text = it.child("hariAktif").value.toString()
//            binding.textNumber.text = it.child("phone").value.toString()
        }
        ref = FirebaseDatabase.getInstance().getReference("anterian")
        ref.child(idAnterian).get().addOnSuccessListener {
            bottomSheet = BottomSheetBatalFragment(
                idAnterian,
                it.child("email").value.toString(),
                it.child("nama").value.toString(),
                it.child("namaToko").value.toString(),
                it.child("noTelp").value.toString(),
                it.child("status").value.toString(),
                it.child("uid").value.toString(),
                it.child("urutan").value.toString(),
                it.child("waktuDibuat").value.toString(),
                it.child("waktuDipanggil").value.toString()
            )

            binding.buttonBatal.setOnClickListener {
                bottomSheet.show(supportFragmentManager, "BottomSheet")
            }
        }
//        binding.imageWA.setOnClickListener { goWA(binding.textNumber.toString()) }
//        binding.textNumber.setOnClickListener { goWA(binding.textNumber.toString()) }
        binding.imageBackToko.setOnClickListener {
            onBackPressed()
        }
    }

    fun goWA(phone: String) {
        val numberEdit = phone.subSequence(1, phone.length)
        val url = "https://api.whatsapp.com/send?phone=+62$numberEdit"
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
        startActivity(intent)
    }
}