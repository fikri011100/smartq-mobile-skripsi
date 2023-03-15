package com.smartqid.smartq.revamp.ui.merchant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentMerchantBinding
import com.smartqid.smartq.revamp.common.BaseFragment
import com.smartqid.smartq.revamp.domain.model.Favorite
import com.smartqid.smartq.revamp.utils.ext.showToast
import java.text.SimpleDateFormat
import java.util.*

class MerchantFragment : BaseFragment<FragmentMerchantBinding>() {

    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    lateinit var setPreferences: SharedPreferences

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMerchantBinding
        get() = FragmentMerchantBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        auth = FirebaseAuth.getInstance()
        ref = Firebase.database.reference
        setPreferences = requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        var verified = false
        var noUrutan: String

        var imageLike = 0
        val nama = arguments?.getString("nama")
        val uid = arguments?.getString("uid")
        val kategori = arguments?.getString("kategori")
        val gambar = arguments?.getString("gambar")
        val alamat = arguments?.getString("alamat")
        val kota = arguments?.getString("kota")
        val waktuBuka = arguments?.getString("waktuBuka")
        val hariAktif = arguments?.getString("hariAktif")
        val desc = arguments?.getString("desc")
        val phone = arguments?.getString("phone")
        val idAnterian = arguments?.getString("idAnterian")
        val estimasi = arguments?.getString("estimasi")
        val instagram = arguments?.getString("instagram")
        val facebook = arguments?.getString("facebook")
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        if (idAnterian.equals("")) {
            val Editor: SharedPreferences.Editor = setPreferences.edit()
            Editor.putInt("urutan", 0)
            Editor.apply()
        } else {
            ref.child("anterian").child(idAnterian!!).get().addOnSuccessListener {
                val Editor: SharedPreferences.Editor = setPreferences.edit()
                Editor.putInt("urutan", it.child("urutan").value.toString().toInt())
                Editor.apply()
            }
        }
        Logger.d(estimasi)
        val bottomSheetFragment =
            ButtonSheetFragment(auth.currentUser.email, nama!!, currentDate, currentDate, uid!!, waktuBuka.toString(), hariAktif.toString(), estimasi!!)
        ref.child("favorite").orderByChild("userId").equalTo(auth.uid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (h in snapshot.children) {
                    if (uid.equals(h.child("tokoId").value.toString())) {
                        binding.imageLike.setImageResource(R.drawable.ic_love_selected)
                        imageLike = 1
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        ref.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
            if (it.child("telephone").value.toString() != "" || it.child("address").value.toString() != "")
                verified = true

            val Editor: SharedPreferences.Editor = setPreferences.edit()
            Editor.putString("name", it.child("name").value.toString())
            Editor.putString("telephone", it.child("telephone").value.toString())
            Editor.apply()
        }
        binding.consListPrice.setOnClickListener {
            findNavController().navigate(R.id.action_tokoFragment_to_listMenuFragment)
        }
        binding.imageLike.setOnClickListener {
            if (imageLike == 0) {
                binding.imageLike.setImageResource(R.drawable.ic_love_selected)
                imageLike = 1
                addFavorite(uid)
                showToast("$nama telah ditambahkan ke wishlist")
            } else {
                binding.imageLike.setImageResource(R.drawable.ic_love_black)
                imageLike = 0
                deleteFavorite(uid)
                showToast("$nama telah dihapus dari wishlist")
            }
        }
        binding.imageWA.setOnClickListener {
            val numberEdit = phone?.subSequence(1, phone.length)
            val url = "https://api.whatsapp.com/send?phone=+62$numberEdit"
            val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
            requireActivity().startActivity(intent)
        }
        binding.imageInstagram.setOnClickListener {
            val url = "http://instagram.com/_u/$instagram"
            val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
            requireActivity().startActivity(intent)
        }
        binding.imageFacebook.setOnClickListener {
            val url = "https://www.facebook.com/$facebook"
            val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
            requireActivity().startActivity(intent)
        }
        binding.buttonMulaiAntri.setOnClickListener {
            if (verified == true) {
                bottomSheetFragment.show(childFragmentManager, "BottomSheetDialog")
            } else {
                Toast.makeText(
                    requireContext(),
                    "Mohon isi Nomor telepon dan Alamat terlebih dahulu...",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.imageBackToko.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.textNamaToko.text = nama
        Glide.with(requireContext()).load(gambar).into(binding.imageToko)
        binding.detailData.text = alamat
        binding.detailKota.text = kota
        binding.textDesc.text = desc
    }

    fun addFavorite(uid: String) {
        ref.child("favorite").push().setValue(
            Favorite(
                auth.uid.toString(),
                uid
            )
        )
    }

    fun deleteFavorite(uidToko: String) {
        ref.child("favorite").orderByChild("userId").equalTo(auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (h in snapshot.children) {
                    for (i in h.children) {
                        Logger.d(i.ref.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}