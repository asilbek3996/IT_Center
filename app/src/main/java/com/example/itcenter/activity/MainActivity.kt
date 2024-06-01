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
    private val loadingFragment = LoadingFragment.newInstance()

    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var refresh: ImageView
    private lateinit var settings: ImageView
    private lateinit var viewModel: MainViewModel

    private val handler = Handler()
    private var fragmentSelected = false

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
            .add(R.id.flContainer, loadingFragment, loadingFragment.tag).commit()

        refresh.setOnClickListener {
            loadData()
            progressBar.visibility = View.VISIBLE
            refresh.visibility = View.GONE
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            fragmentSelected = true
            val fragment = when (it.itemId) {
                R.id.home -> {
                    updateUIForHome()
                    homeFragment
                }

                R.id.favorite -> {
                    updateUIForOther("Tanlanganlar")
                    favoriteFragment
                }

                R.id.game -> {
                    updateUIForQuiz()
                    quizFragment
                }

                R.id.profile -> {
                    updateUIForOther("Hisob")
                    profileFragment
                }

                else -> null
            }
            fragment?.let { switchFragment(it) }
            true
        }

        viewModel.shimmer.observe(this) { status ->
            when (status) {
                1 -> {
                    handler.postDelayed({
                        if (!fragmentSelected) {
                            switchFragment(homeFragment)
                        }
                    }, 1000)
                }

                2 -> {
                    showErrorDialog()
                }
            }
        }
//        viewModel.categoriesData.observe(this){
//            if (it!=null) {
//                viewModel.insertAllDBCategory(it)
//                EventBus.getDefault().post(loadData())
//            }
//        }
        viewModel.studentData.observe(this) {
            Toast.makeText(this, "${it.size}", Toast.LENGTH_SHORT).show()
            if (it!=null) {
                viewModel.insertAllDBStudents(it)
                Toast.makeText(this, "Qo'shildi", Toast.LENGTH_SHORT).show()
                EventBus.getDefault().post(loadData())
            }
        }

        viewModel.userData.observe(this) {
            if (it.isEmpty()) {
                Toast.makeText(this, "Sizning ID raqamingiz serverda topilmadi.", Toast.LENGTH_LONG)
                    .show()
            var pref = PrefUtils(this)
            pref.clear()
            startActivity(Intent(this, CheckActivity::class.java))
            finish()
            }
        }
    }

    private fun loadData() {
        var pref = PrefUtils(this)
        var idRaqam = pref.getID()
        viewModel.getStudent()
        viewModel.getUser(idRaqam)
//        viewModel.getCategoris()
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setMessage("Serverga ulanishda xatolik yuz berdi. Iltimos, yana bir bor urinib ko'ring")
            .setPositiveButton("Qayta urinib ko'ring") { dialog, _ ->
                loadData()
                dialog.dismiss()
            }
            .show()
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, fragment, fragment.tag)
            .commit()
    }

    private fun updateUIForHome() {
        binding.crown1.visibility = View.GONE
        binding.crown2.visibility = View.GONE
        settings.visibility = View.VISIBLE
        refresh.visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvMain).text = "Asosiy"
    }

    private fun updateUIForOther(text: String) {
        binding.crown1.visibility = View.GONE
        binding.crown2.visibility = View.GONE
        settings.visibility = View.VISIBLE
        refresh.visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvMain).text = text
    }

    private fun updateUIForQuiz() {
        binding.crown1.visibility = View.VISIBLE
        binding.crown2.visibility = View.VISIBLE
        settings.visibility = View.GONE
        refresh.visibility = View.GONE
        binding.refresh.visibility = View.GONE
        binding.refreshProgress.visibility = View.GONE
        findViewById<TextView>(R.id.tvMain).text = "Quiz"
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
