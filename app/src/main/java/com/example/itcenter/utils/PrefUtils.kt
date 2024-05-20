package com.example.itcenter.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefUtils(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("IntArrayPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()

    // Int ma'lumotni arrayga qo'shib saqlash funksiyasi
    fun saveFavorite(key: String, value: Int) {
        val intArray = getFavorite(key)?.toMutableList() ?: mutableListOf()
        if (intArray.filter { it == value }.firstOrNull() != null) {
            intArray.remove(value)
            val json = gson.toJson(intArray)
            editor.putString(key, json)
            editor.apply()
        } else {
            intArray.add(value)
            val json = gson.toJson(intArray)
            editor.putString(key, json)
            editor.apply()
        }

    }

    // Saqlangan arrayni olish funksiyasi
    fun getFavorite(key: String): IntArray? {
        val json = sharedPreferences.getString(key, null) ?: return null
        val type = object : TypeToken<List<Int>>() {}.type
        val intList: List<Int> = gson.fromJson(json, type)
        return intList.toIntArray()
    }
    fun checkFavorite(key: String,value: Int): Boolean{
        val intArray = getFavorite(key)?.toMutableList() ?: mutableListOf()
        return intArray.filter { it==value }.firstOrNull() != null
    }
}