package com.smartqid.smartq.revamp.ui.home.profile

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.databinding.ActivityEditProfileBinding
import com.smartqid.smartq.revamp.common.BaseActivity

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>() {

    private lateinit var ref: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var gender: String
    private lateinit var name: String

    override val bindingInflater: (LayoutInflater) -> ActivityEditProfileBinding
        get() = ActivityEditProfileBinding::inflate

    override fun initView() {
        ref = Firebase.database.reference
        mAuth = Firebase.auth

        ref.child("users").child(mAuth.uid.toString()).get().addOnSuccessListener {
            Log.d("valueee", it.child("telephone").value.toString())
            if (it.child("telephone").value != null) {
                binding.edtNoTelp.setText(it.child("telephone").value.toString())
            }
            if (it.child("address").value != null) {
                binding.edtAlamat.setText(it.child("address").value.toString())
            }
            if (it.child("name").value != null) {
                name = it.child("name").value.toString()
            } else {
                name = ""
            }
            if (it.child("gender").value != null) {
                gender = it.child("gender").value.toString()
            } else {
                gender = ""
            }
        }.addOnFailureListener {
            Log.d("error", it.message.toString())
        }
        binding.btnSave.setOnClickListener {
            saveToDatabase(binding.edtNoTelp.text.toString(), binding.edtAlamat.text.toString())
        }
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun saveToDatabase(telephone: String, address: String) {
        //mendapatkan id dari user saat register
        val uid = mAuth.uid
        //menginisiasi instance database
        val database = Firebase.database
        //menambahkan referensi
        val myRef = database.getReference("users/$uid")

        val userModel = HashMap<String, String>()
        userModel["name"] = name
        userModel["email"] = mAuth.currentUser.email
        userModel["telephone"] = telephone
        userModel["address"] = address
        userModel["gender"] = gender

        myRef.setValue(userModel)
        onBackPressed()
        Toast.makeText(this, "Pengaturan berhasil disimpan!", Toast.LENGTH_LONG).show()
    }
}