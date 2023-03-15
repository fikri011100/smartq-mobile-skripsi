package com.smartqid.smartq.revamp.ui.home.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentProfileBinding
import com.smartqid.smartq.revamp.ui.auth.LoginActivity


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        ref = Firebase.database.reference
        val currentUser = auth.currentUser

        ref.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
            binding.textName.text = it.child("name").value.toString()
            binding.textEmail.text =  it.child("email").value.toString()
            Glide.with(requireContext()).load(currentUser.photoUrl).into(binding.profileImage)
        }

        binding.textDataPribadi.setOnClickListener {
            val intent = Intent(requireContext(), DetailProfileActivity::class.java)
            intent.putExtra("status", "edit_profile")
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity!!.finishAffinity()
        }
    }
}