package com.smartqid.smartq.revamp.di

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartqid.smartq.revamp.domain.model.Transaction
import com.smartqid.smartq.revamp.domain.model.response.ResponseTransaction

class HistoryRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val productRef: DatabaseReference = rootRef.child("anterian")
) {

    fun addAntrian(
        email: String, nama: String,
        namaToko: String, noTelp: String,
        uid: String, waktuDibuat: String,
        waktuDipanggil: String
    ) {
        productRef.push().setValue(
            Transaction(
                uid,
                email,
                nama,
                noTelp,
                namaToko,
                "0",
                waktuDipanggil,
                waktuDibuat,
                "Diterima"
            )
        )
    }

    fun cancelAntrian(
        email: String, nama: String,
        namaToko: String, noTelp: String,
        alasan: String, uid: String,
        urutan: String, waktuDibuat: String,
        waktuDipanggil: String
    ) {
        val database = Firebase.database
        val myRef = database.getReference("anterian/$uid")
        var map = HashMap<String, Any>()
        map.put("email", email)
        map.put("nama", nama)
        map.put("namaToko", namaToko)
        map.put("noTelp", noTelp)
        map.put("status", "Dibatalkan")
        map.put("alasan", alasan)
        map.put("uid", uid)
        map.put("urutan", urutan)
        map.put("waktuDibuat", waktuDibuat)
        map.put("waktuDipanggil", waktuDipanggil)
        myRef.setValue(map)
    }

    fun getAntrian() : MutableLiveData<ResponseTransaction> {
        val mutableLiveData = MutableLiveData<ResponseTransaction>()
        val auth = FirebaseAuth.getInstance()
        var query: Query = productRef.orderByChild("email").equalTo(auth.currentUser.email)
        var list : ArrayList<Transaction> = arrayListOf()
        var listUID: ArrayList<String> = arrayListOf()
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val response = ResponseTransaction()
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        if (h.child("status").value.toString().equals("Diterima") || h.child("status").value.toString().equals("Menunggu Konfirmasi Batal")) {
                            val item = h.getValue(Transaction::class.java)
                            list.add(item!!)
                            listUID.add(h.key.toString())
                        }
                    }
                    response.products = list
                    response.listUid = listUID
                } else {
                    response.exception = Exception("error")
                }
                mutableLiveData.value = response
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return mutableLiveData
    }

    fun getHistory() : MutableLiveData<ResponseTransaction> {
        val mutableLiveData = MutableLiveData<ResponseTransaction>()
        val auth = FirebaseAuth.getInstance()
        val query: Query = productRef.orderByChild("email").equalTo(auth.currentUser.email)
        val list : ArrayList<Transaction> = arrayListOf()
        var listUID: ArrayList<String> = arrayListOf()
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val response = ResponseTransaction()
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        if (h.child("status").value.toString().equals("Selesai") || h.child("status").value.toString().equals("Dibatalkan")) {
                            val item = h.getValue(Transaction::class.java)
                            list.add(item!!)
                            listUID.add(h.key.toString())
                        }
                    }
                    response.products = list
                } else {
                    response.exception = Exception("error")
                }
                mutableLiveData.value = response
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return mutableLiveData
    }
}