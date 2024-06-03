package com.example.itcenter.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itcenter.R
import com.example.itcenter.ShowProgress
import com.example.itcenter.adapter.AllCategoryAdapter
import com.example.itcenter.adapter.SearchStudentAdapter
import com.example.itcenter.adapter.StudentAdapter
import com.example.itcenter.databinding.ActivityAllStudentBinding
import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.viewmodel.MainViewModel

class AllStudentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllStudentBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter: SearchStudentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        loadData()
        binding.swipe.setOnRefreshListener {
            loadData()
        }
        viewModel.progress.observe(this, Observer {
            binding.swipe.isRefreshing = it
        })
        binding.back.setOnClickListener {
            finish()
        }
        binding.search.setOnClickListener {
            toggleLayoutVisibility()
        }
        binding.ivExit.setOnClickListener {
            binding.linearlayout1.visibility = View.GONE
            binding.linearlayout2.visibility = View.VISIBLE
        }
        val message = intent.getStringExtra("Group")
        val name = intent.getStringExtra("GroupName")
        binding.tvGroup.text = name
        viewModel.studentData.observe(this){
            getStudent(it,message!!)
        }
        viewModel.studentData.observe(this){
            adapter = SearchStudentAdapter(it)
            binding.recyclerSearchStudent.layoutManager = GridLayoutManager(this,3)
            binding.recyclerSearchStudent.adapter = adapter
        }
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.ivExit.setOnClickListener {
                    if (newText.isNullOrEmpty()) {
                        binding.linearlayout1.visibility = View.GONE
                        binding.linearlayout2.visibility = View.VISIBLE
                    } else {
                        binding.searchview.setQuery("", false)
                    }
                }
                if (newText.isNullOrEmpty()) {
                    binding.recyclerSearchStudent.visibility = View.GONE
                    binding.recyclerStudent.visibility = View.VISIBLE
                    loadData()
                } else {
                    binding.recyclerSearchStudent.visibility = View.VISIBLE
                    binding.recyclerStudent.visibility = View.GONE
                    filter(newText, message!!)
                }

                return true
            }
        })

    }
    fun loadData(){
        viewModel.getStudent()
    }
    fun getStudent(students: List<AllStudentModel>, message: String){
        var item = arrayListOf<AllStudentModel>()

        for (student in students){
            if (student.group==message){
                item.add(student)
            }
        }
        binding.recyclerStudent.layoutManager = GridLayoutManager(this,3)
        binding.recyclerStudent.adapter = StudentAdapter(item)
    }
    fun filter(text: String, message: String){
        val filters: ArrayList<AllStudentModel> = ArrayList()
        viewModel.studentData.observe(this, Observer {
            var item = arrayListOf<AllStudentModel>()

            for (student in it){
                if (student.group==message){
                    item.add(student)
                }
            }
            for (language in item){
                if (language.fullName.toLowerCase().contains(text.toLowerCase())){
                    filters.add(language)
                }
            }
            adapter.filter(filters)
        })
    }
    private fun toggleLayoutVisibility() {
        // SearchViewning joriy visibility holatini aniqlash
        val currentVisibility = binding.search.visibility
        // Agar SearchView ko'rsatilmoqda bo'lsa
        if (currentVisibility == View.VISIBLE) {
            // LinearLayout1ni ko'rsatish va LinearLayout2ni yashirish
            binding.linearlayout1.visibility = View.VISIBLE
            binding.linearlayout2.visibility = View.GONE
        } else {
            // Aks holda, LinearLayout1ni yashirish va LinearLayout2ni ko'rsatish
            binding.linearlayout1.visibility = View.GONE
            binding.linearlayout2.visibility = View.VISIBLE
        }
    }
}