package com.example.itcenter.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.itcenter.fragment.*
import com.example.itcenter.R
import com.example.itcenter.ShowProgress
import com.example.itcenter.databinding.ActivityMainBinding
import com.example.itcenter.model.viewmodel.MainViewModel
import com.example.itcenter.utils.PrefUtils
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity(), ShowProgress.View {
    private val homeFragment = HomeFragment.newInstance()
    private val favoriteFragment = FavoriteFragment.newInstance()
    private val quizFragment = QuizFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()
    var activeFragment:Fragment = homeFragment

    private lateinit var binding: ActivityMainBinding
    private lateinit var settings: ImageView
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        loadData()
        settings = findViewById(R.id.settings)
        settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, favoriteFragment, favoriteFragment.tag).hide(favoriteFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, quizFragment, quizFragment.tag).hide(quizFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, profileFragment, profileFragment.tag).hide(profileFragment)
            .commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()
        binding.refreshMain.setOnClickListener {
            // Tugma bosilganda holatini ViewModelga uzatish
            viewModel.setButtonClicked(true)
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.actionHome) {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(homeFragment)
                        .commit()
                    activeFragment = homeFragment
                    binding.tvMain.text = "Asosiy"
                binding.crown1.visibility = View.GONE
                binding.crown2.visibility = View.GONE
                settings.visibility = View.VISIBLE
                binding.refreshMain.visibility = View.VISIBLE
                binding.refreshProgressMain.visibility = View.GONE
            } else if (it.itemId == R.id.actionFavorite) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(favoriteFragment).commit()
                activeFragment = favoriteFragment
                binding.tvMain.text = "Tanlanganlar"
                binding.crown1.visibility = View.GONE
                binding.crown2.visibility = View.GONE
                settings.visibility = View.VISIBLE
                binding.refreshMain.visibility = View.VISIBLE
                binding.refreshProgressMain.visibility = View.GONE
                favoriteFragment.updateData()
            } else if (it.itemId == R.id.actionGame) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(quizFragment)
                    .commit()
                activeFragment = quizFragment
                binding.tvMain.text = "Quiz"
                binding.crown1.visibility = View.VISIBLE
                binding.crown2.visibility = View.VISIBLE
                settings.visibility = View.GONE
                binding.refreshMain.visibility = View.GONE
                binding.refreshProgressMain.visibility = View.GONE
            } else if (it.itemId == R.id.actionProfile) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
                    .commit()
                activeFragment = profileFragment
                binding.tvMain.text = "Hisob"
                binding.crown1.visibility = View.GONE
                binding.crown2.visibility = View.GONE
                settings.visibility = View.VISIBLE
                binding.refreshMain.visibility = View.VISIBLE
                binding.refreshProgressMain.visibility = View.GONE
            }

            true
        }
        viewModel.studentData.observe(this) {
            if (it!=null) {
                viewModel.insertAllDBStudents(it)
                EventBus.getDefault().post(loadData())
            }
        }
viewModel.progress.observe(this){
    if (it){
        showProgressBar()
    }else{
        hideProgressBar()

    }
}
    }

    private fun loadData() {
        viewModel.getStudent()
    }

    override fun showProgressBar() {
        binding.refreshProgressMain.visibility = View.VISIBLE
        binding.refreshMain.visibility = View.GONE
    }

    override fun hideProgressBar() {
        binding.refreshProgressMain.visibility = View.GONE
        binding.refreshMain.visibility = View.VISIBLE
    }
}
