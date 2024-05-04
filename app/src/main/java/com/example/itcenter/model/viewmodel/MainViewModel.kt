package com.example.itcenter.model.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itcenter.api.repository.Repository
import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.ImageItem

class MainViewModel: ViewModel() {
    val repository = Repository()


    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val adsData = MutableLiveData<List<ImageItem>>()
    val allCategoryData = MutableLiveData<List<AllCategoryModel>>()
    val categoriesData = MutableLiveData<ArrayList<CategoryModel>>()

    fun getOffers() {
        repository.getAds(error, progress, adsData)
    }
    fun getAllCategories() {
        repository.getAllCategories(error, progress, allCategoryData)
    }
    fun getCategoris() {
        repository.getCategories(error, progress, categoriesData)
    }
}