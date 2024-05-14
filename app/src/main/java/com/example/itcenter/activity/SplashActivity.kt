package com.example.itcenter.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.itcenter.databinding.ActivitySplashBinding
import com.example.itcenter.model.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val idRaqami = sharedPreferences.getInt("id", -1)
        val malumotBoSh = idRaqami == -1
        binding.animationViews.postDelayed({
            if (malumotBoSh) {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    startActivity(Intent(this@SplashActivity, CheckActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    val editor = sharedPreferences.edit()
                    editor.putString("group", "x")
                    editor.apply()
                }
            }else{
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            finish()
        },2950)
    }

}