package com.example.itcenter.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.itcenter.utils.PrefUtils
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityVideoBinding
import com.example.itcenter.model.viewmodel.MainViewModel
import com.example.itcenter.utils.Constants

class VideoActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var message = intent.getIntExtra("video", -1)
        viewModel.getLessons()
        viewModel.lessonsData.observe(this) {
            for (video in it) {
                if (video.id == message) {
                    binding.tvLanguage.text = video.lessonName
                    binding.webView.settings.javaScriptEnabled = true
                    binding.webView.webChromeClient = MyChrome()
                    binding.webView.webViewClient = WebViewClient()
                    binding.webView.loadUrl(video.videoLink)


                    val pref = PrefUtils(this)

                    if (pref.checkFavorite(Constants.favorite, message)) {
                        binding.save.setImageResource(R.drawable.heart)
                    } else {
                        binding.save.setImageResource(R.drawable.favorite)
                    }

                    binding.save.setOnClickListener {
                        pref.saveFavorite(Constants.favorite, message)
                        if (pref.checkFavorite(Constants.favorite, message)) {
                            binding.save.setImageResource(R.drawable.heart)
                        } else {
                            binding.save.setImageResource(R.drawable.favorite)
                        }
                    }


                    binding.back2.setOnClickListener {
                        finish()
                    }

                }
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