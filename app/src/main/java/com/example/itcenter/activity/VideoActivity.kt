package com.example.itcenter.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityVideoBinding
import com.example.itcenter.model.DarslarModel

class VideoActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoBinding
    lateinit var message: DarslarModel
    private lateinit var imageView1 : ImageView
    private var isImage1Selected = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        message = intent.getSerializableExtra("video") as DarslarModel
        binding.tvLanguage.text = message.lessonName
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webChromeClient = MyChrome()
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(message.videoLink)
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        var edit = sharedPreferences.edit()
        imageView1= findViewById(R.id.save)
        imageView1.setOnClickListener {
            if (isImage1Selected) {
                edit.putInt("favorite",message.id)
                edit.apply()
                imageView1.setImageResource(R.drawable.heart)
                isImage1Selected = false
            } else {
                imageView1.setImageResource(R.drawable.favorite)
                isImage1Selected = true
            }
        }
    }
    private inner class MyChrome : WebChromeClient() {
        private var fullscreen: View? = null

        @SuppressLint("SetJavaScriptEnabled")
        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
            super.onShowCustomView(view, callback)
            binding.webView.visibility = View.GONE

            if (fullscreen != null) {
                (window.decorView as FrameLayout).removeView(fullscreen)
            }

            fullscreen = view
            (window.decorView as FrameLayout).addView(fullscreen, FrameLayout.LayoutParams(-1, -1))
            fullscreen?.visibility = View.VISIBLE
        }

        override fun onHideCustomView() {
            super.onHideCustomView()
            fullscreen?.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE
        }
    }
}