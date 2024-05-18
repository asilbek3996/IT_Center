package com.example.itcenter.api.repository

import androidx.lifecycle.MutableLiveData
import com.example.itcenter.api.NetworkManager
import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.BaseResponse
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.DarslarModel
import com.example.itcenter.model.ImageItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class Repository() {
    val compositeDisposable = CompositeDisposable()
    fun clearDisposable() {
        compositeDisposable.clear()
    }
    fun getAds(error: MutableLiveData<String>, progress:MutableLiveData<Boolean>, success:MutableLiveData<ArrayList<ImageItem>>){
        progress.value = true
        compositeDisposable.add(NetworkManager.getApiService().getAds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ArrayList<ImageItem>>(){
                override fun onNext(t: ArrayList<ImageItem>) {
                    progress.value = false
                    success.value = t
                }

                override fun onError(e: Throwable) {
                    progress.value = false
                    error.value = e.localizedMessage
                }

                override fun onComplete() {
                }
            })
        )

    }
    fun getAllCategories(error: MutableLiveData<String>, success:MutableLiveData<List<AllCategoryModel>>,progress: MutableLiveData<Boolean>){
        progress.value = true
        compositeDisposable.add(NetworkManager.getApiService().getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<AllCategoryModel>>(){
                override fun onNext(t: List<AllCategoryModel>) {
                    progress.value = false
                    success.value = t
                }

                override fun onError(e: Throwable) {
                    progress.value = false
                    error.value = e.localizedMessage
                }

                override fun onComplete() {
                }
            })
        )

    }
    fun getCategories(error: MutableLiveData<String>, success:MutableLiveData<ArrayList<CategoryModel>>){
        compositeDisposable.add(NetworkManager.getApiService().getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ArrayList<CategoryModel>>(){
                override fun onNext(t: ArrayList<CategoryModel>) {
                    success.value = t
                }

                override fun onError(e: Throwable) {
                    error.value = e.localizedMessage
                }

                override fun onComplete() {
                }
            })
        )

    }
    fun getStudent(error: MutableLiveData<String>, success:MutableLiveData<ArrayList<AllStudentModel>>,progress: MutableLiveData<Boolean>,shimmer: MutableLiveData<Int>){
        progress.value = true
        shimmer.value = 0
        compositeDisposable.add(NetworkManager.getApiService().getStudent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ArrayList<AllStudentModel>>(){
                override fun onNext(t: ArrayList<AllStudentModel>) {
                    progress.value = false
                    shimmer.value = 1
                    success.value = t
                }

                override fun onError(e: Throwable) {
                    progress.value = false
                    shimmer.value = 2
                    error.value = e.localizedMessage
                }

                override fun onComplete() {
                }
            })
        )

    }
    fun getLessons(error: MutableLiveData<String>, success:MutableLiveData<ArrayList<DarslarModel>>,progress: MutableLiveData<Boolean>){
        progress.value = true
        compositeDisposable.add(NetworkManager.getApiService().getLessons()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ArrayList<DarslarModel>>(){
                override fun onNext(t: ArrayList<DarslarModel>) {
                    progress.value = false
                    success.value = t
                }

                override fun onError(e: Throwable) {
                    progress.value = false
                    error.value = e.localizedMessage
                }

                override fun onComplete() {
                }
            })
        )

    }
    }