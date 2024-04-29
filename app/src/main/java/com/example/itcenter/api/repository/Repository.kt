package com.example.itcenter.api.repository

import androidx.lifecycle.MutableLiveData
import com.example.itcenter.api.NetworkManager
import com.example.itcenter.model.BaseResponse
import com.example.itcenter.model.ImageItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class Repository() {
    val compositeDisposable = CompositeDisposable()
    fun getAds(error: MutableLiveData<String>, progress:MutableLiveData<Boolean>, success:MutableLiveData<List<ImageItem>>){
        compositeDisposable.add(NetworkManager.getApiService().getAds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ImageItem>>(){
                override fun onNext(t: List<ImageItem>) {
                    progress.value = false
                    success.value = t
//                    if (t.success){
//                        success.value = t.data
//                    }else{
//                        error.value = t.message
//                    }
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