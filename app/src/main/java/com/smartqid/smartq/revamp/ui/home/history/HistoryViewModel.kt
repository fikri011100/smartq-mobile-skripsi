package com.smartqid.smartq.revamp.ui.home.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smartqid.smartq.revamp.di.HistoryRepository
import com.smartqid.smartq.revamp.domain.model.response.ResponseTransaction

class HistoryViewModel : ViewModel() {

    private val repository = HistoryRepository()

    fun getAntrianLiveData() : LiveData<ResponseTransaction> {
        return repository.getAntrian()
    }

    fun getHistoryLiveData() : LiveData<ResponseTransaction> {
        return repository.getHistory()
    }
}