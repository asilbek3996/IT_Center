package com.example.itcenter.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityQuizSplashBinding

class QuizSplashActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.animationView.postDelayed({
            startActivity(Intent(this,QuizStartActivity::class.java))
            finish()
        },3500)
    }
}