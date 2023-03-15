package com.smartqid.smartq.revamp.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smartqid.smartq.revamp.di.KategoriRepository
import com.smartqid.smartq.revamp.domain.model.response.ResponseStore

class KategoriViewModel : ViewModel() {
    private val repository = KategoriRepository()

    fun getToko(kategori: String) : LiveData<ResponseStore> {
        return repository.getToko(kategori)
    }
}