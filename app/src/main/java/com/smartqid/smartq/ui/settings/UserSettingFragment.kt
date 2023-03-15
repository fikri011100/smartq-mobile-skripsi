package com.smartqid.smartq.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.R

class UserSettingFragment : Fragment() {

    private lateinit var ref: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var address: String
    private lateinit var number: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = Firebase.database.reference
        mAuth = Firebase.auth
        var gender: String = ""

//        ref.child("users").child(mAuth.uid.toString()).get().addOnSuccessListener {
//            Log.d("valueee", it.child("name").value.toString())
//            if (it.child("name").value != null)
//                edt_nama.setText(it.child("name").value.toString())
//
//            if (it.child("gender").value != null) {
//                if (it.child("gender").value == "Laki-laki") {
//                    radioGroup.check(R.id.men)
//                } else {
//                    radioGroup.check(R.id.women)
//                }
//            }
//
//
//            radioGroup.setOnCheckedChangeListener { radioGroup, i ->
//                var rb = view.findViewById<RadioButton>(i)
//                if (rb != null) {
//                    when (rb.text) {
//                        "Laki-laki" ->
//                            gender = "Laki-laki"
//                        "Perempuan" ->
//                            gender = "Perempuan"
//                    }
//                }
//            }
//            if (it.child("telephone").value != null) {
//                number = it.child("telephone").value.toString()
//            } else {
//                number = ""
//            }
//            if (it.child("address").value != null) {
//                address = it.child("address").value.toString()
//            } else {
//                address = ""
//            }
//        }.addOnFailureListener {
//            Log.d("error", it.message.toString())
//        }
//        btn_save.setOnClickListener {
//            saveToDatabase(edt_nama.text.toString(), gender)
//        }
    }

    private fun saveToDatabase(name: String, gender: String) {
        //mendapatkan id dari user saat register
        val uid = mAuth.uid
        //menginisiasi instance database
        val database = Firebase.database
        //menambahkan referensi
        val myRef = database.getReference("users/$uid")

        val userModel = HashMap<String, String>()
        userModel["name"] = name
        userModel["email"] = mAuth.currentUser.email
        userModel["telephone"] = number
        userModel["address"] = address
        userModel["gender"] = gender

        myRef.setValue(userModel)
        requireActivity().onBackPressed()
        Toast.makeText(requireContext(), "Pengaturan berhasil disimpan!", Toast.LENGTH_LONG).show()
    }

}