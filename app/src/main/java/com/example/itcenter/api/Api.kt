package com.example.itcenter.api

import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.DarslarModel
import com.example.itcenter.model.ImageItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("ads")
    fun getAds(): Observable<ArrayList<ImageItem>>
    @GET("category")
    fun getAllCategories(): Observable<List<AllCategoryModel>>
    @GET("category")
    fun getCategories(): Observable<ArrayList<CategoryModel>>
    @GET("students")
    fun getStudent(): Observable<ArrayList<AllStudentModel>>
    @GET("students")
    fun getUsers(@Query("id")id :Int): Observable<AllStudentModel>
    @GET("lessonLevel")
    fun getLessons(): Observable<ArrayList<DarslarModel>>
}