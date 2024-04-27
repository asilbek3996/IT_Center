package com.example.homepage.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.itcenter.R
import com.example.itcenter.activity.DarslarActivity
import com.example.itcenter.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel("https://bit.ly/2YoJ77H",ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel("https://bit.ly/2BteuF2",ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", ScaleTypes.CENTER_CROP))

        val imageSlider = requireActivity().findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList);

        val searchView: SearchView = requireActivity().findViewById(R.id.search_view)
        val editText: EditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(resources.getColor(android.R.color.black))
        editText.setHintTextColor(resources.getColor(R.color.gray))

        val intent = Intent(requireContext(), DarslarActivity::class.java)
        binding.kotlin.setOnClickListener {
            intent.apply {
              putExtra("Til","Kotlin")
            }
            startActivity(intent)
        }
        binding.java.setOnClickListener {
            intent.apply {
              putExtra("Til","Java")
            }
            startActivity(intent)
        }
        binding.python.setOnClickListener {
            intent.apply {
                putExtra("Til","Python")
            }
            startActivity(intent)
        }
        binding.cpp.setOnClickListener {
            intent.apply {
                putExtra("Til","C++")
            }
            startActivity(intent)
        }
        binding.scratch.setOnClickListener {
            intent.apply {
                putExtra("Til","Scratch")
            }
            startActivity(intent)
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}