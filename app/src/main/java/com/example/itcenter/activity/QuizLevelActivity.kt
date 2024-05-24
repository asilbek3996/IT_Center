package com.example.itcenter.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itcenter.R
import com.example.itcenter.adapter.QuizLanguage
import com.example.itcenter.adapter.QuizLevelAdapter
import com.example.itcenter.databinding.ActivityQuizLevelBinding
import com.example.itcenter.model.QuizLevelModel

class QuizLevelActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizLevelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val message = intent.getStringExtra("Language")
        val list = listOf(
            QuizLevelModel(1,"1-bosqich"),
            QuizLevelModel(2,"2-bosqich"),
            QuizLevelModel(3,"3-bosqich"),
            QuizLevelModel(4,"4-bosqich"),
            QuizLevelModel(5,"5-bosqich"),
            QuizLevelModel(6,"6-bosqich"),
            QuizLevelModel(7,"7-bosqich"),
            QuizLevelModel(8,"8-bosqich"),
            QuizLevelModel(9,"9-bosqich"),
            QuizLevelModel(10,"10-bosqich"),
        )
        binding.back.setOnClickListener {
            finish()
        }
        binding.recyclerQuiz.layoutManager = LinearLayoutManager(this)
        binding.recyclerQuiz.adapter = QuizLevelAdapter(list,this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,QuizLanguageActivity::class.java))
        finish()
    }
}