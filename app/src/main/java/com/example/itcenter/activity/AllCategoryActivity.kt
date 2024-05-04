package com.example.itcenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itcenter.adapter.AllCategoryAdapter
import com.example.itcenter.databinding.ActivityAllCategoryBinding
import com.example.itcenter.model.viewmodel.MainViewModel

class AllCategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllCategoryBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.back.setOnClickListener {
            finish()
        }

        viewModel.allCategoryData.observe(this) {
            binding.recyclerAllCategory.layoutManager = GridLayoutManager(this, 3)
            binding.recyclerAllCategory.adapter = AllCategoryAdapter(it)
        }
        viewModel.getAllCategories()
    }
}