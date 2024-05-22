package com.example.itcenter.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itcenter.R
import com.example.itcenter.activity.QuizSplashActivity
import com.example.itcenter.activity.QuizStartActivity
import com.example.itcenter.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    lateinit var binding: FragmentQuizBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.play.setOnClickListener {
            startActivity(Intent(requireActivity(),QuizSplashActivity::class.java))
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = QuizFragment()
    }
}