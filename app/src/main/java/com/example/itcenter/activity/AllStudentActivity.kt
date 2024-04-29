package com.example.itcenter.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityAllStudentBinding

class AllStudentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val message = intent.getStringExtra("Group")

    }
}