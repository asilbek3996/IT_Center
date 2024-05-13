package com.example.itcenter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.homepage.fragments.HomeFragment
import com.example.itcenter.R
import com.example.itcenter.ShowProgress
import com.example.itcenter.activity.MainActivity
import com.example.itcenter.databinding.FragmentHomeBinding
import com.example.itcenter.databinding.FragmentLoadingBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.ImageItem
import com.example.itcenter.model.viewmodel.MainViewModel
import com.example.itcenter.utils.Constants

class LoadingFragment : Fragment() {
    lateinit var binding: FragmentLoadingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmerLayout.startShimmer()
    }
    companion object {
        @JvmStatic
        fun newInstance() = LoadingFragment()
    }
}