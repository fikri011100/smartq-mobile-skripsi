package com.smartqid.smartq.revamp.ui.home.profile

import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.ActivityDetailProfileBinding
import com.smartqid.smartq.revamp.common.BaseActivity

class DetailProfileActivity : BaseActivity<ActivityDetailProfileBinding>() {

    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var genders = ""

    override val bindingInflater: (LayoutInflater) -> ActivityDetailProfileBinding
        get() = ActivityDetailProfileBinding::inflate

    override fun initView() {
        val status = intent.getStringExtra("status")
        auth = FirebaseAuth.getInstance()

        val gender = resources.getStringArray(R.array.arr_gender)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender)
        binding.spnJenisKelamin.adapter = adapter

        binding.imageBackToko.setOnClickListener {
            onBackPressed()
        }

        ref = Firebase.database.reference
        if (status.equals("edit_profile")) {
            val currentUser = auth.currentUser

            ref.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
                binding.textName.text = it.child("name").value.toString()
                Glide.with(applicationContext).load(currentUser.photoUrl).into(binding.profileImage)
                binding.edtName.setText(it.child("name").value.toString())
                binding.edtAlamat.setText(it.child("address").value.toString())
                binding.edtNoTelp.setText(it.child("telephone").value.toString())

                if (it.child("gender").equals("Perempuan")) {
                    binding.spnJenisKelamin.setSelection(1)
                } else {
                    binding.spnJenisKelamin.setSelection(0)
                }
                binding.spnJenisKelamin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position == 1) {
                            genders = "Perempuan"
                        } else {
                            genders = "Laki-laki"
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }

                binding.btnSave.setOnClickListener {
                    saveToDatabase(
                        binding.edtNoTelp.text.toString(),
                        binding.edtAlamat.text.toString(),
                        binding.edtName.text.toString()
                    )
                }
            }
        }
    }

    private fun saveToDatabase(telephone: String, address: String, name: String) {
        //mendapatkan id dari user saat register
        val uid = auth.uid
        //menginisiasi instance database
        val database = Firebase.database
        //menambahkan referensi
        val myRef = database.getReference("users/$uid")

        val userModel = HashMap<String, String>()
        userModel["name"] = name
        userModel["email"] = auth.currentUser.email
        userModel["telephone"] = telephone
        userModel["address"] = address
        userModel["gender"] = genders

        myRef.setValue(userModel)
        onBackPressed()
        Toast.makeText(this, "Pengaturan berhasil disimpan!", Toast.LENGTH_LONG).show()
    }
}