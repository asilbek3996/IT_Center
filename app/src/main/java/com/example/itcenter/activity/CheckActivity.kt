package com.example.itcenter.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityCheckBinding
import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.orhanobut.hawk.Hawk

class CheckActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        showCustomDialogBox()
        showAlertDialog()
        binding.signUp.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
        binding.btnLogin.setOnClickListener {
            var text = binding.etEmail.text.toString()
            viewModel.studentData.observe(this, Observer {
                for (language in it){
                    if (language.id.toString() == text){
                        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putInt("id", language.id)
                        editor.apply()
                        startActivity(Intent(this@CheckActivity,MainActivity::class.java))
                        finish()
                    }
                }
            })
        }
        viewModel.getStudent()
    }
    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Siz IT Center Dang'ara filliali o'quvchisimisiz")
        alertDialogBuilder.setPositiveButton("Ha", DialogInterface.OnClickListener { dialog, which ->
            binding.linearLayout.visibility = View.VISIBLE
            dialog.dismiss()
        })
        alertDialogBuilder.setNegativeButton("Yo'q", DialogInterface.OnClickListener { dialog, which ->
            // Agar "Cancel" bosilsa qilish kerak bo'lgan har qanday ishlar
            startActivity(Intent(this@CheckActivity,RegisterActivity::class.java))
            finish()
            dialog.dismiss()
        })
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}