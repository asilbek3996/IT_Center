package com.example.itcenter.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
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
        var kotlin = arrayListOf<DarslarModel>()
        for (darslar in list) {
            if (darslar.language==message){
                kotlin.add(darslar)
            }
        }
        binding.recyclerDars.layoutManager = GridLayoutManager(this,3)
        binding.recyclerDars.adapter = DarsAdapter(kotlin)
        binding.back2.setOnClickListener {
            binding.main2.transitionToStart()
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


}