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
    private lateinit var progressBar: ProgressBar
    private lateinit var refresh: ImageView
    private lateinit var settings: ImageView
    private lateinit var viewModel: MainViewModel


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

        refresh.setOnClickListener {
            loadData()
            progressBar.visibility = View.VISIBLE
            refresh.visibility = View.GONE
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
                refresh.visibility = View.VISIBLE
            } else if (it.itemId == R.id.actionFavorite) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(favoriteFragment).commit()
                activeFragment = favoriteFragment
                binding.tvMain.text = "Tanlanganlar"
                binding.crown1.visibility = View.GONE
                binding.crown2.visibility = View.GONE
                settings.visibility = View.VISIBLE
                refresh.visibility = View.VISIBLE
            } else if (it.itemId == R.id.actionGame) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(quizFragment)
                    .commit()
                activeFragment = quizFragment
                binding.tvMain.text = "Quiz"
                binding.crown1.visibility = View.VISIBLE
                binding.crown2.visibility = View.VISIBLE
                settings.visibility = View.GONE
                refresh.visibility = View.GONE
                binding.refresh.visibility = View.GONE
                binding.refreshProgress.visibility = View.GONE
            } else if (it.itemId == R.id.actionProfile) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
                    .commit()
                activeFragment = profileFragment
                binding.tvMain.text = "Hisob"
                binding.crown1.visibility = View.GONE
                binding.crown2.visibility = View.GONE
                settings.visibility = View.VISIBLE
                refresh.visibility = View.VISIBLE
            }

            true
        }
//        viewModel.categoriesData.observe(this){
//            if (it!=null) {
//                viewModel.insertAllDBCategory(it)
//                EventBus.getDefault().post(loadData())
//            }
//        }
        viewModel.studentData.observe(this) {
            if (it!=null) {
                viewModel.insertAllDBStudents(it)
                EventBus.getDefault().post(loadData())
            }
        }

//        viewModel.userData.observe(this) {
//            if (it.isEmpty()) {
//                Toast.makeText(this, "Sizning ID raqamingiz serverda topilmadi.", Toast.LENGTH_LONG)
//                    .show()
//            var pref = PrefUtils(this)
//            pref.clear()
//            startActivity(Intent(this, CheckActivity::class.java))
//            finish()
//            }
//        }
    }

    private fun loadData() {
        var pref = PrefUtils(this)
        var idRaqam = pref.get_ID()
        viewModel.getStudent()
//        if (idRaqam != null) {
//            viewModel.getUser(idRaqam)
//        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().hide(activeFragment).show(fragment)
            .commit()
        activeFragment = fragment
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
        switchFragment(homeFragment)
    }
}
