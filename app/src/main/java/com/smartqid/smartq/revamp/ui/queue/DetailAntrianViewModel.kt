package com.smartqid.smartq.revamp.ui.queue

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smartqid.smartq.revamp.di.HistoryRepository
import com.smartqid.smartq.revamp.domain.model.response.ResponseTransaction

class DetailAntrianViewModel : ViewModel() {

    private val repository = HistoryRepository()

    fun addAntrian(email: String, nama: String,
                   namaToko: String, noTelp: String,
                   uid: String, waktuDibuat: String,
                   waktuDipanggil: String) {
        repository.addAntrian(
            email, nama, namaToko, noTelp, uid, waktuDibuat, waktuDipanggil
        )
    }
}