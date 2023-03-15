package com.smartqid.smartq.ui.home.beranda.dialog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.SettingDialogBinding
import com.smartqid.smartq.revamp.ui.auth.LoginActivity
import com.smartqid.smartq.revamp.ui.home.profile.EditProfileActivity
import com.smartqid.smartq.ui.home.riwayat.RiwayatActivity
import com.smartqid.smartq.ui.settings.SettingActivity

class SettingDialog : DialogFragment(R.layout.setting_dialog) {
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: SettingDialogBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SettingDialogBinding.bind(view)

        binding.constraintNotification.setOnClickListener {
            Toast.makeText(context, "Comming Soon", Toast.LENGTH_LONG).show()
        }
        binding.constraintPromosi.setOnClickListener {
            Toast.makeText(context, "Comming Soon", Toast.LENGTH_LONG).show()
        }
        binding.constraintPengaturan.setOnClickListener {
            val intent = Intent(context, SettingActivity::class.java)
            requireActivity().startActivity(intent)
        }
        binding.constraintRiwayat.setOnClickListener {
            val intent = Intent(context, RiwayatActivity::class.java)
            requireActivity().startActivity(intent)
        }
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            requireActivity().startActivity(intent)
        }
        binding.constraintLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())

            // set title
            builder.setTitle("Logout")

            //set content area
            builder.setMessage("Apakah anda yakin untuk logout?")

            //set negative button
            builder.setPositiveButton(
                "Ya"
            ) { dialog, id ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                Toast.makeText(requireContext(), "Logging Out", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                requireActivity().finish()
            }
            builder.setNegativeButton(
                "Tidak"
            ) { dialog, id ->
                // User clicked Update Now button
            }
            builder.show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        ref = Firebase.database.reference
        val currentUser = auth.currentUser

        ref.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
            binding.textNama.text = it.child("name").value.toString()
        }
        Glide.with(requireContext()).load(currentUser.photoUrl).into(binding.imageProfile)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(1000, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
}