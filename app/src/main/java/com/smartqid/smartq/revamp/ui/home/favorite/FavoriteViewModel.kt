package com.smartqid.smartq.revamp.ui.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smartqid.smartq.revamp.di.FavoriteRepository
import com.smartqid.smartq.revamp.domain.model.response.ResponseFavorite

class FavoriteViewModel : ViewModel() {

    private val repository = FavoriteRepository()

    fun getResponseUsingLiveData() : LiveData<ResponseFavorite> {
        return repository.getFavorite()
    }
}