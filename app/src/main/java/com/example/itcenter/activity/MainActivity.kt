package com.example.itcenter.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.homepage.fragments.FavoriteFragment
import com.example.homepage.fragments.HomeFragment
import com.example.homepage.fragments.ProfileFragment
import com.example.homepage.fragments.QuizFragment
import com.example.itcenter.R
import com.example.itcenter.ShowProgress
import com.example.itcenter.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), ShowProgress.View {
    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val quizFragment = QuizFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    var activeFragment: Fragment = homeFragment
    lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var refresh: ImageView
    private lateinit var settings: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = findViewById(R.id.refreshProgress)
        settings = findViewById(R.id.settings)
        refresh = findViewById(R.id.refresh)
        settings.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
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
        val bottomN = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val tvMain = findViewById<TextView>(R.id.tvMain)

        bottomN.setOnItemSelectedListener {
            if (it.itemId == R.id.home) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment)
                    .commit()
                activeFragment = homeFragment
                tvMain.text = "Asosiy"
            } else if (it.itemId == R.id.favorite) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(favoriteFragment).commit()
                activeFragment = favoriteFragment
                tvMain.text = "Tanlanganlar"
            } else if (it.itemId == R.id.game) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(quizFragment)
                    .commit()
                activeFragment = quizFragment
                tvMain.text = "Testlar"
            } else if (it.itemId == R.id.profile) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
                    .commit()
                activeFragment = profileFragment
                tvMain.text = "Profil"
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
}