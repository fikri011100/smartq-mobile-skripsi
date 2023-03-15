package com.smartqid.smartq.revamp.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.ActivityHomeBinding
import com.smartqid.smartq.revamp.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate
    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private lateinit var gender: String
    private lateinit var name: String
    private lateinit var telephone: String
    private lateinit var address: String

    fun SetUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_host
        ) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun initView() {
        val fm = supportFragmentManager
        val fgDetail = fm.findFragmentById(R.id.detailKategori)
        val fgToko = fm.findFragmentById(R.id.tokoFragment)
        mAuth = Firebase.auth
        ref = Firebase.database.reference

        ref.child("users").child(mAuth.uid.toString()).get().addOnSuccessListener {
            name = it.child("name").value.toString()
            gender = it.child("gender").value.toString()
            telephone = it.child("telephone").value.toString()
            address = it.child("address").value.toString()

            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (it.isSuccessful) {
                    val token = it.result
                    Log.d("FirebaseToken", token)
                    saveToDatabase(token)
                }
            }
        }

        when {
            fgToko != null -> {
                binding.bottomNavigationView.visibility = View.GONE
            }
            fgDetail != null -> {
                binding.bottomNavigationView.visibility = View.GONE
            }
            else -> {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        SetUpNavigation()
    }

    private fun saveToDatabase(token: String) {
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
        userModel["token"] = token

        myRef.setValue(userModel)
    }

//    fun passData(data: String) {
//        setPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//        val Editor: SharedPreferences.Editor = setPreferences.edit()
//        Editor.putString("Kategori", data)
//        Editor.apply()
//    }
}