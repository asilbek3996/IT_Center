package com.example.itcenter

import com.orhanobut.hawk.Hawk

object PrefUtils {
    fun setFavorite(item: Int){
        val items = Hawk.get("ID", Int)
        Hawk.put("ID", items)
    }
}