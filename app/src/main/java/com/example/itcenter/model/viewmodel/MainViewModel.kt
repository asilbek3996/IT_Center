package com.example.itcenter.model.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itcenter.api.repository.Repository
import com.example.itcenter.model.ImageItem

class MainViewModel: ViewModel() {
    val repository = Repository()


    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val adsData = MutableLiveData<List<ImageItem>>()

    fun getOffers() {
        repository.getAds(error, progress, adsData)
    }
}