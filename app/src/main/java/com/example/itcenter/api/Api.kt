package com.example.itcenter.api

import com.example.itcenter.model.BaseResponse
import com.example.itcenter.model.ImageItem
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {
    @GET("ads")
    fun getAds(): Observable<List<ImageItem>>
}