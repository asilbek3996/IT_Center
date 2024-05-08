package com.example.itcenter.model.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itcenter.api.repository.Repository
import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.ImageItem

class MainViewModel: ViewModel() {
    val repository = Repository()


    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val adsData = MutableLiveData<ArrayList<ImageItem>>()
    val allCategoryData = MutableLiveData<List<AllCategoryModel>>()
    val searchCategoryData = MutableLiveData<List<AllCategoryModel>>()
    val categoriesData = MutableLiveData<ArrayList<CategoryModel>>()
    val studentData = MutableLiveData<ArrayList<AllStudentModel>>()

    fun getOffers() {
        repository.getAds(error, progress, adsData)
    }
    fun getAllCategories() {
        repository.getAllCategories(error, allCategoryData)
    }
    fun getSearchCategories(search: String) {
        repository.getSearchCategories(error, searchCategoryData,search)
    }
    fun getCategoris() {
        repository.getCategories(error, categoriesData)
    }
    fun getStudent() {
        repository.getStudent(error, studentData)
    }
}