package com.example.itcenter.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itcenter.R
import com.example.itcenter.adapter.DarsAdapter
import com.example.itcenter.databinding.ActivityDarslarBinding
import com.example.itcenter.model.DarslarModel

class DarslarActivity : AppCompatActivity() {
    lateinit var binding: ActivityDarslarBinding
    var check: Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarslarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val message = intent.getStringExtra("Til")
        var kotlin1 = getString(R.string.kotlin1)
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val group = sharedPreferences.getString("group", null)
        var list = arrayListOf(
            DarslarModel(1,"https://www.youtube.com/embed/cTpLaTwmq8M?si=ygXz03hj79nEmQdU",
                kotlin1,"Kotlin", "1-dars",R.drawable.kotlin),
            DarslarModel(2,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/cTpLaTwmq8M?si=ygXz03hj79nEmQdU\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                kotlin1,"Kotlin", "2-dars",R.drawable.kotlin),
            DarslarModel(3,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/cTpLaTwmq8M?si=ygXz03hj79nEmQdU\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                kotlin1,"Kotlin", "3-dars",R.drawable.kotlin),
            DarslarModel(4,"<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/cTpLaTwmq8M?si=ygXz03hj79nEmQdU\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen=true></iframe>",
                kotlin1,"Kotlin", "4-dars",R.drawable.kotlin),
        )
        binding.tvLanguage.text = message
        if (message == group) {
            var kotlin = arrayListOf<DarslarModel>()
            for (darslar in list) {
                if (darslar.language == message) {
                    kotlin.add(darslar)
                }
            }
            binding.back.setOnClickListener {
                finish()
            }
            binding.search.setOnClickListener {
                toggleLayoutVisibility() // toggleLayoutVisibility funksiyasini chaqirish
            }
            binding.recyclerDars.layoutManager = GridLayoutManager(this, 3)
            binding.recyclerDars.adapter = DarsAdapter(kotlin)
            binding.back2.setOnClickListener {
                binding.main2.transitionToStart()
            }
            binding.ivExit.setOnClickListener {
                binding.linearlayout1.visibility = View.GONE
                binding.linearlayout2.visibility = View.VISIBLE
            }
            binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Bu metodni qo'shmaymiz
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
                    return true
                }
            })
        }else if (group == "x"){
            var txt = "Siz hech qaysi guruhda o'qimaysi "
            showAlertDialog(txt,message!!)
        }else{
            var text = "Siz $group guruhida o'qiysiz "
            showAlertDialog(text,message!!)
        }
        }

    @SuppressLint("SetJavaScriptEnabled")
    fun onItemClick(item: DarslarModel) {

        binding.tvSubtitr.text = item.subtitr
        binding.tvName.text = item.name
        binding.webView.loadData(item.video,"text/html","utf-8")
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webChromeClient = WebChromeClient()
        check = true

        binding.main2.transitionToEnd()
    }

    override fun onBackPressed() {
        binding.main2.transitionToStart()
        super.onBackPressed()
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

    private fun showAlertDialog(text: String,cource:String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("$text $cource darslari sizga ochiq emas.\nAgar $cource darslari sizga qiziq bo'lsa telegram botimiz orqali uni sotib olishingiz mumkin.")
        alertDialogBuilder.setPositiveButton("Sotib olish", DialogInterface.OnClickListener { dialog, which ->
            val link = "https://t.me/dangara_itcenterbot" // Sizning linkingizni o'zgartiring
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
            finish()
        })
        alertDialogBuilder.setNegativeButton("Ortga qaytish", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            finish()
        })
        alertDialogBuilder.setCancelable(false)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}