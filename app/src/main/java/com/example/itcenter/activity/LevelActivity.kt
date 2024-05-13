package com.example.itcenter.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityLevelBinding
import com.example.itcenter.model.viewmodel.MainViewModel

class LevelActivity : AppCompatActivity() {
    lateinit var binding: ActivityLevelBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val message = intent.getStringExtra("Til")
        viewModel.categoriesData.observe(this){
            for (item in it){
                if (item.language == message){
                    Glide.with(binding.img).load(item.image).into(binding.img)
                    Glide.with(binding.topImage).load(item.image).into(binding.topImage)
                }
            }
        }
        var txt = ""
        if (message == "Kompyuter Savodxonligi" || message == "Microsoft Office"){
            txt = "$message darslari"
        }else{
            txt = "$message dasturlash tili"
        }
        binding.tvName.text = txt
        if (message=="Kotlin") {
            binding.ivBegin.setImageResource(R.drawable.kotlin_logo)
            binding.ivFree.setImageResource(R.drawable.kotlin_logo)
            binding.ivMedium.setImageResource(R.drawable.kotlin_logo)
            binding.ivAdvanced.setImageResource(R.drawable.kotlin_logo)
        }else if (message=="Java"){
            binding.ivBegin.setImageResource(R.drawable.java_logo)
            binding.ivFree.setImageResource(R.drawable.java_logo)
            binding.ivMedium.setImageResource(R.drawable.java_logo)
            binding.ivAdvanced.setImageResource(R.drawable.java_logo)
        }else if (message=="Python"){
            binding.ivBegin.setImageResource(R.drawable.python_logo)
            binding.ivFree.setImageResource(R.drawable.python_logo)
            binding.ivMedium.setImageResource(R.drawable.python_logo)
            binding.ivAdvanced.setImageResource(R.drawable.python_logo)
        }else if (message=="C++"){
            binding.ivBegin.setImageResource(R.drawable.cpp_logo)
            binding.ivFree.setImageResource(R.drawable.cpp_logo)
            binding.ivMedium.setImageResource(R.drawable.cpp_logo)
            binding.ivAdvanced.setImageResource(R.drawable.cpp_logo)
        }else if (message=="Scratch"){
            binding.ivBegin.setImageResource(R.drawable.scratch_logo)
            binding.ivFree.setImageResource(R.drawable.scratch_logo)
            binding.ivMedium.setImageResource(R.drawable.scratch_logo)
            binding.ivAdvanced.setImageResource(R.drawable.scratch_logo)
        }
        binding.back.setOnClickListener {
            finish()
        }
        val intent = Intent(this, DarslarActivity::class.java)
        binding.free.setOnClickListener {
            intent.apply {
                putExtra("level", "free")
                putExtra("Til", message)
            }
            startActivity(intent)
        }
        binding.begin.setOnClickListener {
            intent.apply {
                putExtra("level", "begin")
                putExtra("Til", message)
            }
            startActivity(intent)
        }
        binding.medium.setOnClickListener {
            intent.apply {
                putExtra("level", "medium")
                putExtra("Til", message)
            }
            startActivity(intent)
        }
        binding.advanced.setOnClickListener {
            intent.apply {
                putExtra("level", "advanced")
                putExtra("Til", message)
            }
            startActivity(intent)
        }
        viewModel.getCategoris()
    }
}