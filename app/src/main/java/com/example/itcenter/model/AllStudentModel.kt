package com.example.itcenter.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class AllStudentModel(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val _id: String,
    var id: Int,
    var fullName: String,
    var birthday: String,
    var address: String,
    var group: String,
    var personalPhone: String,
    var homePhone: String,
    var certificate: Boolean,
    var graduated: Boolean,
    var userPercentage: Int,
    var userPhoto: String?
)
