package com.example.itcenter.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itcenter.R
import com.example.itcenter.adapter.CategoryAdapter
import com.example.itcenter.databinding.ActivityAllCategoryBinding
import com.example.itcenter.model.CategoryModel

class AllCategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            finish()
        }
        val categoryList = arrayListOf(
            CategoryModel(R.drawable.kotlin,"Kotlin"),
            CategoryModel(R.drawable.java,"Java"),
            CategoryModel(R.drawable.cpp,"C++"),
            CategoryModel(R.drawable.python,"Python"),
            CategoryModel(R.drawable.scratch,"Scratch")
        )
        binding.recyclerAllCategory.layoutManager = GridLayoutManager(this,3)
        binding.recyclerAllCategory.adapter = CategoryAdapter(categoryList)

    }
}