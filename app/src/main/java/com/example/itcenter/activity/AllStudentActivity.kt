package com.example.itcenter.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itcenter.R
import com.example.itcenter.adapter.StudentAdapter
import com.example.itcenter.databinding.ActivityAllStudentBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.viewmodel.MainViewModel

class AllStudentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllStudentBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        loadData()
        binding.back.setOnClickListener {
            finish()
        }
        val message = intent.getStringExtra("Group")
        if (message=="Java, Kotlin"){
            binding.tvGroup.text = "Java, Kotlin"
        }else if (message=="Python, C++"){
            binding.tvGroup.text = "Python, C++"
        }else if (message=="Scratch"){
            binding.tvGroup.text = "Scratch"
        }else if (message=="Literacy"){
            binding.tvGroup.text = "Literacy"
        }
        viewModel.studentData.observe(this){
            getStudent(it,message!!)
        }
    }
    fun loadData(){
        viewModel.getStudent()
    }
    fun getStudent(students: ArrayList<AllStudentModel>, message: String){
        var item = arrayListOf<AllStudentModel>()

        for (student in students){
            if (student.group==message){
                item.add(student)
            }
        }
        binding.recyclerStudent.layoutManager = GridLayoutManager(this,3)
        binding.recyclerStudent.adapter = StudentAdapter(item)
    }
}