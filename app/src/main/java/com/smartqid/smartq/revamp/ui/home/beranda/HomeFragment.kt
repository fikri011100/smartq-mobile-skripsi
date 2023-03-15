package com.smartqid.smartq.revamp.ui.home.beranda

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.R
import com.smartqid.smartq.common.MarginDecolator
import com.smartqid.smartq.databinding.FragmentHomeBinding
import com.smartqid.smartq.revamp.common.BaseFragment
import com.smartqid.smartq.revamp.domain.model.Category
import com.smartqid.smartq.revamp.ui.adapter.KategoriAdapter
import com.smartqid.smartq.revamp.ui.home.notification.NotificationActivity
import com.smartqid.smartq.ui.home.beranda.dialog.SettingDialog
import timber.log.Timber

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var setPreferences: SharedPreferences
    private var bannerToggle: Int = 0
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        auth = FirebaseAuth.getInstance()
        ref = Firebase.database.reference
        val currentUser = auth.currentUser
        val firestore = FirebaseFirestore.getInstance()

        ref.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
            binding.textNama.text = it.child("name").value.toString()
        }
        setPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        bannerToggle = setPreferences.getInt("banner", 0)

        binding.consNotification.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }
        getKategori(firestore)
        setBannerToggle()
        Glide.with(requireContext()).load(currentUser.photoUrl).into(binding.profileImage)

        binding.profileImage.setOnClickListener {
//            showSettingDialog()
        }
    }
    private fun setBannerToggle() {
//        if (bannerToggle == 1) {
//            binding.imageviewBanner.setImageResource(R.drawable.diterima)
////            updateToko()
//        } else {
//            binding.imageviewBanner.setImageResource(R.drawable.productive)
//        }
//        val Editor: SharedPreferences.Editor = setPreferences.edit()
//        Editor.putInt("banner", 0)
//        Editor.apply()
    }

    private fun showSettingDialog() {
        val setting = SettingDialog()
        setting.show(childFragmentManager, "CreateDialog")

    }

    private fun getKategori(db: FirebaseFirestore) {
        var data: ArrayList<Category>?
        data = arrayListOf()
        db.collection("KategoriID").get()
            .addOnSuccessListener { result ->
                data = ArrayList()
                for (document in result) {
                    data!!.add(
                        Category(
                            document.data["icon"].toString(),
                            document.data["namaKategori"].toString()
                        )
                    )
                    Timber.d("Datanya : => ${document.data["namaKategori"]} | ${document.data["icon"]}")
                }
                ref = FirebaseDatabase.getInstance().getReference("toko")
                val kategoriAdapter = KategoriAdapter(data!!, ref, requireContext())
                binding.listKategori.apply {
                    adapter = kategoriAdapter

                    layoutManager = GridLayoutManager(requireContext(), 4)
                    val firstLast = resources.getDimension(R.dimen.kat_first).toInt()
                    val rightLeftDefault = resources.getDimension(R.dimen.kat_first).toInt()
                    val topBottomDefault = resources.getDimension(R.dimen.dp8).toInt()

                    val decorator = MarginDecolator(firstLast, rightLeftDefault, topBottomDefault)
                    addItemDecoration(decorator)
                }
                binding.pgBeranda.visibility = View.GONE
                binding.listKategori.visibility = View.VISIBLE
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents : $exception")
            }
    }

}