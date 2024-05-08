package com.example.itcenter.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itcenter.adapter.AllCategoryAdapter
import com.example.itcenter.databinding.ActivityAllCategoryBinding
import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.viewmodel.MainViewModel
import java.util.Locale

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
        binding.search.setOnClickListener {
            toggleLayoutVisibility()
        }
        binding.ivExit.setOnClickListener {
            binding.linearlayout1.visibility = View.GONE
            binding.linearlayout2.visibility = View.VISIBLE
        }
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.ivExit.setOnClickListener {
                    if (newText.isNullOrEmpty()) {
                        binding.linearlayout1.visibility = View.GONE
                        binding.linearlayout2.visibility = View.VISIBLE
                    } else {
                        binding.searchview.setQuery("", false)
                    }
                }
                if (newText.isNullOrEmpty()) {
                    binding.recyclerSearchCategory.visibility = View.GONE
                    binding.recyclerAllCategory.visibility = View.VISIBLE
                    viewModel.getAllCategories()
                } else {
                    binding.recyclerSearchCategory.visibility = View.VISIBLE
                    binding.recyclerAllCategory.visibility = View.GONE

                    viewModel.searchCategoryData.observe(this@AllCategoryActivity){
                        binding.recyclerSearchCategory.layoutManager = GridLayoutManager(this@AllCategoryActivity, 3)
                        binding.recyclerSearchCategory.adapter = AllCategoryAdapter(it)
                    }
                    viewModel.getSearchCategories(newText)
                }

                return true
            }
        })


        viewModel.allCategoryData.observe(this) {
            binding.recyclerAllCategory.layoutManager = GridLayoutManager(this, 3)
            binding.recyclerAllCategory.adapter = AllCategoryAdapter(it)
        }
        viewModel.getAllCategories()
    }

    private fun toggleLayoutVisibility() {
        // SearchViewning joriy visibility holatini aniqlash
        val currentVisibility = binding.search.visibility
        // Agar SearchView ko'rsatilmoqda bo'lsa
        if (currentVisibility == View.VISIBLE) {
            // LinearLayout1ni ko'rsatish va LinearLayout2ni yashirish
            binding.linearlayout1.visibility = View.VISIBLE
            binding.linearlayout2.visibility = View.GONE
        } else {
            // Aks holda, LinearLayout1ni yashirish va LinearLayout2ni ko'rsatish
            binding.linearlayout1.visibility = View.GONE
            binding.linearlayout2.visibility = View.VISIBLE
        }
    }
}