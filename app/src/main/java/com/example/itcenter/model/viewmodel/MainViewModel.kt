package com.example.itcenter.model.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itcenter.api.repository.Repository
import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.DarslarModel
import com.example.itcenter.model.ImageItem
import com.example.itcenter.model.QuestionModel
import com.example.itcenter.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    val repository = Repository()


    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val shimmer = MutableLiveData<Int>()
    val adsData = MutableLiveData<ArrayList<ImageItem>>()
    val allCategoryData = MutableLiveData<List<AllCategoryModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val studentData = MutableLiveData<List<AllStudentModel>>()
    val userData = MutableLiveData<ArrayList<AllStudentModel>>()
    val lessonsData = MutableLiveData<ArrayList<DarslarModel>>()
    val questionData = MutableLiveData<ArrayList<QuestionModel>>()


    fun getOffers() {
        repository.getAds(error, adsData,progress,shimmer)
    }
    fun getAllCategories() {
        repository.getAllCategories(error, allCategoryData)
    }
    fun getCategories() {
        repository.getCategories(error, categoriesData)
    }
    fun getStudent() {
        repository.getStudent(error, studentData)
    }
//    fun getUser(id: String) {
//        repository.getUser(id,error, userData, progress,shimmer)
//    }
    fun getLessons() {
        repository.getLessons(error, lessonsData, progress)
    }
    fun getQuestions() {
        repository.getQuestions(error, questionData)
    }
    fun clear() {
        repository.clearDisposable()
    }

    fun insertAllDBStudents(items: List<AllStudentModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase().getStudentDao().deleteAll()
            AppDatabase.getDatabase().getStudentDao().insertAllStudent(items)
        }
    }
    fun getAllStudents(){
        CoroutineScope(Dispatchers.Main).launch {
            studentData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getStudentDao().getAllStudents()}
        }
    }

//    fun insertAllDBCategory(items: List<CategoryModel>) {
//        CoroutineScope(Dispatchers.IO).launch {
//                AppDatabase.getDatabase().getCategoryDao().deleteAllCategory()
//                AppDatabase.getDatabase().getCategoryDao().insertAll(items)
//        }
//    }
//    fun getAllCategory(){
//        CoroutineScope(Dispatchers.Main).launch {
//            categoriesData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getCategoryDao().getAllCategory()}
//        }
//    }
}