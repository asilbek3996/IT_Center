package com.example.itcenter

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.itcenter.room.AppDatabase
import com.orhanobut.hawk.Hawk

class App: MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Hawk.init(this).build()
        AppDatabase.initDatabase(this)
    }
}