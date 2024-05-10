package com.example.itcenter.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityAboutStudentBinding

class AboutStudentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAboutStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var message = intent.getStringExtra("Student")
        binding.about.text = message
    }
}