package com.smartqid.smartq.revamp.di

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.smartqid.smartq.revamp.domain.model.Favorite
import com.smartqid.smartq.revamp.domain.model.response.ResponseFavorite

class FavoriteRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val productRef: DatabaseReference = rootRef.child("favorite")
) {

    fun getFavorite() : MutableLiveData<ResponseFavorite> {
        val mutableLiveData = MutableLiveData<ResponseFavorite>()
        val auth = FirebaseAuth.getInstance()
        val query: Query = productRef.orderByChild("userId").equalTo(auth.uid.toString())
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val response = ResponseFavorite()
                if (snapshot.exists()) {
                    val result = snapshot.children
                    result.let {
                        response.products = snapshot.children.map { snapShot ->
                            snapShot.getValue(Favorite::class.java)!!
                        }
                    }
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