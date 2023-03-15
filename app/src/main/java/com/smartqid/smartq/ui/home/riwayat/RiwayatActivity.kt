package com.smartqid.smartq.ui.home.riwayat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.databinding.ActivityRiwayatBinding
import com.smartqid.smartq.revamp.domain.model.Transaction
import com.smartqid.smartq.revamp.ui.home.history.RiwayatAdapter

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        ref = Firebase.database.reference

        binding.imgBackHomeSetting.setOnClickListener {
            onBackPressed()
        }

        var list: ArrayList<Transaction>?
        list = arrayListOf()
        ref = FirebaseDatabase.getInstance().getReference("anterian")
        val query: Query = ref.orderByChild("email").equalTo(auth.currentUser.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    list = ArrayList()
                    for (h in snapshot.children) {
                        if (h.child("status").value.toString().equals("Selesai") || h.child("status").value.toString().equals("Dibatalkan")) {
                            val item = h.getValue(Transaction::class.java)
                            list?.add(item!!)
                        }
                    }

                    try {
                        val riwayatAdapter = RiwayatAdapter(list!!, applicationContext, ref)
                        binding.dataRiwayat.apply {
                            adapter = riwayatAdapter
                            layoutManager = LinearLayoutManager(applicationContext)
                        }
                        binding.pgRiwayat.visibility = View.GONE
                        binding.dataRiwayat.visibility = View.VISIBLE
                    } catch (e : Exception) {
                        e.printStackTrace()
                    }
                } else {
                    binding.pgRiwayat.visibility = View.GONE
                    binding.imageEmptyRiwayat.visibility = View.VISIBLE
                    binding.textNoData.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}