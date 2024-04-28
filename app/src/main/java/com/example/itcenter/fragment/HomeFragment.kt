package com.example.homepage.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.itcenter.R
import com.example.itcenter.activity.DarslarActivity
import com.example.itcenter.databinding.FragmentHomeBinding
import com.example.itcenter.model.AllStudentModel


class HomeFragment : Fragment() {
lateinit var binding: FragmentHomeBinding
    var proccec = 90
    var handler = Handler()
    var allStudentsList = listOf(
        AllStudentModel(1,"Muxtorov Javohirbek",100,R.drawable.ahmad_aka,"android"),
        AllStudentModel(2,"Rahmataliyev Shamshodbek",50,R.drawable.ahmad_aka,"android"),
        AllStudentModel(3,"Komilov Asilbek",88,R.drawable.ahmad_aka,"android"),
        AllStudentModel(4,"Akramjonov Boburjon",89,R.drawable.ahmad_aka,"python"),
        AllStudentModel(4,"Akramjonov Boburjon",67,R.drawable.ahmad_aka,"python"),
        AllStudentModel(5,"Muxtorov Javohirbek",84,R.drawable.ahmad_aka,"python"),
        AllStudentModel(6,"Muxtorov Javohirbek",23,R.drawable.ahmad_aka,"android"),
        AllStudentModel(7,"Muxtorov Javohirbek",56,R.drawable.ahmad_aka,"android"),
        AllStudentModel(8,"Muxtorov Javohirbek",66,R.drawable.ahmad_aka,"scratch"),
        AllStudentModel(9,"Muxtorov Javohirbek",78,R.drawable.ahmad_aka,"android"),
        AllStudentModel(10,"Muxtorov Javohirbek",90,R.drawable.ahmad_aka,"scratch"),
        AllStudentModel(11,"Muxtorov Javohirbek",99,R.drawable.ahmad_aka,"scratch"),
        AllStudentModel(12,"Muxtorov Javohirbek",100,R.drawable.ahmad_aka,"literacy"),
        AllStudentModel(13,"Muxtorov Javohirbek",97,R.drawable.ahmad_aka,"literacy"),
        AllStudentModel(14,"Muxtorov Javohirbek",89,R.drawable.ahmad_aka,"literacy"),
        AllStudentModel(15,"Muxtorov Javohirbek",90,R.drawable.ahmad_aka,"literacy"),
        AllStudentModel(16,"Muxtorov Javohirbek",54,R.drawable.ahmad_aka,"python"),
        AllStudentModel(17,"Muxtorov Javohirbek",12,R.drawable.ahmad_aka,"python"),
        AllStudentModel(18,"Muxtorov Javohirbek",45,R.drawable.ahmad_aka,"python"),
    )
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
        val imageList = ArrayList<SlideModel>()

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
        var android = arrayListOf<AllStudentModel>()
        for (student in allStudentsList){
            if (student.language=="android"){
                android.add(student)
            }
        }

        val thread = Thread { topAndroid(android) }
        thread.start()


    }
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    private fun topAndroid(students: ArrayList<AllStudentModel>) {
        val top = students.sortedByDescending { it.foiz }.take(3)
        binding.imgAndroid1.setImageResource(top[0].img)
        binding.imgAndroid2.setImageResource(top[1].img)
        binding.imgAndroid3.setImageResource(top[2].img)
        binding.nameAndroid1.text = top[0].name
        binding.nameAndroid2.text = top[1].name
        binding.nameAndroid3.text = top[2].name
        for (value in 0..top[0].foiz){
            try {
                Thread.sleep(10)
                binding.progressAndroid1.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizAndroid1.text = topAndroidtxt
            })
        }
        for (value in 0..top[1].foiz){
            try {
                Thread.sleep(10)
                binding.progressAndroid2.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizAndroid2.text = topAndroidtxt
            })
        }
        for (value in 0..top[2].foiz){
            try {
                Thread.sleep(10)
                binding.progressAndroid3.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizAndroid3.text = topAndroidtxt
            })
        }
    }
}