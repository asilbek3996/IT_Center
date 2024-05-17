package com.example.itcenter.model.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itcenter.api.repository.Repository
import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.DarslarModel
import com.example.itcenter.model.ImageItem

class MainViewModel: ViewModel() {
    val repository = Repository()


    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val shimmer = MutableLiveData<Int>()
    val adsData = MutableLiveData<ArrayList<ImageItem>>()
    val allCategoryData = MutableLiveData<List<AllCategoryModel>>()
    val categoriesData = MutableLiveData<ArrayList<CategoryModel>>()
    val studentData = MutableLiveData<ArrayList<AllStudentModel>>()
    val userData = MutableLiveData<AllStudentModel>()
    val lessonsData = MutableLiveData<ArrayList<DarslarModel>>()

    fun getOffers() {
        repository.getAds(error, progress, adsData)
    }
    fun getAllCategories() {
        repository.getAllCategories(error, allCategoryData, progress)
    }
    fun getCategoris() {
        repository.getCategories(error, categoriesData)
    }
    fun getStudent() {
        repository.getStudent(error, studentData, progress,shimmer)
    }
    fun getUser(id: Int) {
        repository.getUser(error, userData, progress,id)
    }
    fun getLessons() {
        repository.getLessons(error, lessonsData, progress)
    }
}