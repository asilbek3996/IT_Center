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
        val tvMain = findViewById<TextView>(R.id.tvMain)
        var check = true
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, loadingFragment, loadingFragment.tag).commit()
        viewModel.shimmer.observe(this){
            if (it == 1) {
                check = false
                binding.main.transitionToEnd()
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
            goto(check)
        }


            binding.bottomNavigationView.setOnItemSelectedListener {
                if (it.itemId == R.id.home) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContainer, homeFragment, homeFragment.tag).commit()
                    tvMain.text = "Asosiy"
                    binding.crown1.visibility = View.GONE
                    binding.crown2.visibility = View.GONE
                    binding.settings.visibility = View.VISIBLE
                    binding.refresh.visibility = View.VISIBLE
                } else if (it.itemId == R.id.favorite) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContainer, favoriteFragment, favoriteFragment.tag).commit()
                    tvMain.text = "Tanlanganlar"
                    binding.crown1.visibility = View.GONE
                    binding.crown2.visibility = View.GONE
                    binding.settings.visibility = View.VISIBLE
                    binding.refresh.visibility = View.VISIBLE
                } else if (it.itemId == R.id.game) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContainer, quizFragment, quizFragment.tag).commit()
                    tvMain.text = "Testlar"
                    binding.crown1.visibility = View.VISIBLE
                    binding.crown2.visibility = View.VISIBLE
                    binding.settings.visibility = View.GONE
                    binding.refresh.visibility = View.GONE
                    binding.refreshProgress.visibility = View.GONE
                } else if (it.itemId == R.id.profile) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flContainer, profileFragment, profileFragment.tag).commit()
                    tvMain.text = "Profil"
                    binding.crown1.visibility = View.GONE
                    binding.crown2.visibility = View.GONE
                    binding.settings.visibility = View.VISIBLE
                    binding.refresh.visibility = View.VISIBLE
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
    fun goto(check: Boolean){
        Handler().postDelayed({
            val tvMain = findViewById<TextView>(R.id.tvMain)
            if (!check){
                tvMain.text = "Asosiy"
                progressBar.visibility = View.GONE
                refresh.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flContainer, homeFragment, homeFragment.tag).commit()
            }
        },2000)
    }
    fun loadData(){
        viewModel.getStudent()
    }
}