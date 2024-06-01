package com.example.itcenter.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itcenter.R
import com.example.itcenter.ShowProgress
import com.example.itcenter.adapter.AllCategoryAdapter
import com.example.itcenter.adapter.QuizLanguage
import com.example.itcenter.adapter.SearchCategoryAdapter
import com.example.itcenter.databinding.ActivityQuizLanguageBinding
import com.example.itcenter.model.viewmodel.MainViewModel

class QuizLanguageActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizLanguageBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter: SearchCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        loadData()
        binding.back.setOnClickListener {
            startActivity(Intent(this,QuizStartActivity::class.java))
            finish()
        }

        binding.refresh.setOnClickListener {
            loadData()
            showProgressBar()
        }
        viewModel.progress.observe(this){
            if (it) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        }
        viewModel.allCategoryData.observe(this) {
            binding.recyclerQuiz.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerQuiz.adapter = QuizLanguage(it,this)
        }
    }

    fun loadData(){
        viewModel.getAllCategories()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,QuizStartActivity::class.java))
        finish()
    }

    fun showProgressBar() {
        binding.refreshProgress.visibility = View.VISIBLE
        binding.refresh.visibility = View.GONE
    }

    fun hideProgressBar() {
        binding.refreshProgress.visibility = View.GONE
        binding.refresh.visibility = View.VISIBLE
    }
}