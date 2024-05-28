package com.example.itcenter.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.itcenter.fragment.FavoriteFragment
import com.example.itcenter.fragment.HomeFragment
import com.example.itcenter.fragment.ProfileFragment
import com.example.itcenter.fragment.QuizFragment
import com.example.itcenter.R
import com.example.itcenter.ShowProgress
import com.example.itcenter.databinding.ActivityMainBinding
import com.example.itcenter.fragment.LoadingFragment
import com.example.itcenter.model.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), ShowProgress.View {
    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val quizFragment = QuizFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    val loadingFragment = LoadingFragment.newInstance()
    lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var refresh: ImageView
    private lateinit var settings: ImageView
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        loadData()
        progressBar = findViewById(R.id.refreshProgress)
        settings = findViewById(R.id.settings)
        refresh = findViewById(R.id.refresh)
        settings.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
        refresh.setOnClickListener {
            loadData()
            progressBar.visibility = View.VISIBLE
            refresh.visibility = View.GONE
        }
        var fragment = "Asosiy"
        var a = 0
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, loadingFragment, loadingFragment.tag).commit()


        var check = true
        viewModel.shimmer.observe(this){
            if (it == 1) {
                check = false
            }else if (it == 2){
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setMessage("There was an error connecting to the server. Please try again")
                alertDialogBuilder.setPositiveButton("try again", DialogInterface.OnClickListener { dialog, which ->
                    loadData()
                    dialog.dismiss()
                })
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
            if (a==0) {
                goto(check, fragment, false)
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.home) {
                binding.crown1.visibility = View.GONE
                binding.crown2.visibility = View.GONE
                binding.settings.visibility = View.VISIBLE
                binding.refresh.visibility = View.VISIBLE
                fragment = "Asosiy"
                a=1
                if (check) {
                    goto(true, fragment, false)
                }else{
                    goto(true,fragment,true)
                }
            } else if (it.itemId == R.id.favorite) {
                binding.crown1.visibility = View.GONE
                binding.crown2.visibility = View.GONE
                binding.settings.visibility = View.VISIBLE
                binding.refresh.visibility = View.VISIBLE
                fragment = "Tanlanganlar"
                a=1
                goto(true,fragment,true)
            } else if (it.itemId == R.id.game) {
                binding.crown1.visibility = View.VISIBLE
                binding.crown2.visibility = View.VISIBLE
                binding.settings.visibility = View.GONE
                binding.refresh.visibility = View.GONE
                binding.refreshProgress.visibility = View.GONE
                fragment = "Testlar"
                a=1
                goto(true,fragment,true)
            } else if (it.itemId == R.id.profile) {
                binding.crown1.visibility = View.GONE
                binding.crown2.visibility = View.GONE
                binding.settings.visibility = View.VISIBLE
                binding.refresh.visibility = View.VISIBLE
                fragment = "Profile"
                a=1
                goto(true,fragment,true)
            }

            return@setOnItemSelectedListener true

        }




    }

    private fun showCustomDialogBox() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.about_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val exit: ImageView = dialog.findViewById(R.id.exit)
        exit.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        refresh.visibility = View.GONE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
        refresh.visibility = View.VISIBLE
    }
    override fun navigateToHomeFragment() {
        val tvMain = findViewById<TextView>(R.id.tvMain)
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, homeFragment, homeFragment.tag).commit()
        tvMain.text = "Asosiy"
    }
    fun goto(check: Boolean,fragment: String,a: Boolean){
            val tvMain = findViewById<TextView>(R.id.tvMain)
            tvMain.text = fragment

            if (a){
                if (fragment == "Asosiy") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContainer, homeFragment, homeFragment.tag).commit()
                }else if (fragment == "Tanlanganlar") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContainer, favoriteFragment, favoriteFragment.tag).commit()
                }else if (fragment == "Testlar") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContainer, quizFragment, quizFragment.tag).commit()
                }else if (fragment == "Profile") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContainer, profileFragment, profileFragment.tag).commit()
                }
            }else {
                Handler().postDelayed({
                if (!check){
                    progressBar.visibility = View.GONE
                    refresh.visibility = View.VISIBLE
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.flContainer, homeFragment, homeFragment.tag).commit()
                }
                },2000)
                }
    }
    fun loadData(){
        viewModel.getStudent()
    }
}