package com.example.itcenter.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.DarslarModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefUtils(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()

    // Int ma'lumotni arrayga qo'shib saqlash funksiyasi
    fun saveFavorite(key: String, value: DarslarModel) {
        val intArray = getFavorite(key)?.toMutableList() ?: mutableListOf()
        if (intArray.filter { it.id == value.id }.firstOrNull() != null) {
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
    fun getFavorite(key: String): ArrayList<DarslarModel>? {
        val json = sharedPreferences.getString(key, null) ?: return null
        val type = object : TypeToken<List<DarslarModel>>() {}.type
        val intList: List<DarslarModel> = gson.fromJson(json, type)
        val arrayList: ArrayList<DarslarModel> = ArrayList(intList)
        return arrayList
    }
    fun checkFavorite(key: String,value: Int): Boolean{
        val intArray = getFavorite(key)?.toMutableList() ?: mutableListOf()
        return intArray.filter { it.id==value }.firstOrNull() != null
    }
    fun setStudent(student: AllStudentModel){
        editor.putInt(Constants.ID, student.id)
        editor.putString(Constants.fullName, student.fullName)
        editor.putString(Constants.group, student.group)
        editor.apply()
    }
    fun getID(): Int {
        return sharedPreferences.getInt(Constants.ID,-1)
    }
    fun getStudent(key: String): String? {
        return sharedPreferences.getString(key,null)
    }
    fun setLevel(value: String){
        editor.putString(Constants.level,value)
    }
    fun setLesson(value: Int){
        editor.putInt(Constants.lesson,value)
    }
    fun getLevel(): String? {
        return sharedPreferences.getString(Constants.level,"begin")
    }
    fun getLesson(): Int {
        return sharedPreferences.getInt(Constants.lesson,0)
    }
    fun clear(){
        editor.clear()
        editor.apply()
    }
}