package com.smartqid.smartq.revamp.di

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.smartqid.smartq.revamp.domain.model.Store
import com.smartqid.smartq.revamp.domain.model.response.ResponseStore
import timber.log.Timber

class KategoriRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val productRef: DatabaseReference = rootRef.child("toko")
) {

    fun getToko(kategori: String) : MutableLiveData<ResponseStore> {
        val mutableLiveData = MutableLiveData<ResponseStore>()
        val auth = FirebaseAuth.getInstance()
        var list : ArrayList<Store> = arrayListOf()
        val query: Query = productRef.orderByChild("kategori").equalTo(kategori)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val response = ResponseStore()
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        val item = h.getValue(Store::class.java)
                        if (item?.idAnterian != null) {
                            list?.add(
                                Store(
                                    h.key,
                                    item.namaToko,
                                    item.kategori,
                                    item.gambar,
                                    item.gambarToko,
                                    item.alamat,
                                    item.kota,
                                    item.waktuBuka,
                                    item.hariAktif,
                                    item.desc,
                                    item.phone,
                                    item.idAnterian,
                                    item.estimasi,
                                    item.instagram,
                                    item.facebook
                                )
                            )
                        } else {
                            list?.add(
                                Store(
                                    h.key,
                                    item?.namaToko,
                                    item?.kategori,
                                    item?.gambar,
                                    item?.gambarToko,
                                    item?.alamat,
                                    item?.kota,
                                    item?.waktuBuka,
                                    item?.hariAktif,
                                    item?.desc,
                                    item?.phone,
                                    "0",
                                    item?.estimasi,
                                    item?.instagram,
                                    item?.facebook
                                )
                            )
                        }
                        Timber.d(h.toString())
                    }
                    response.products = list
                } else {
                    response.exception = Exception("error")
                }
                mutableLiveData.value = response
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return mutableLiveData
    }
}