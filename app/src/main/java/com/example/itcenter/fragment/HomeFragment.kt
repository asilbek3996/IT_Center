package com.example.homepage.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.itcenter.R
import com.example.itcenter.activity.AllCategoryActivity
import com.example.itcenter.activity.AllStudentActivity
import com.example.itcenter.activity.DarslarActivity
import com.example.itcenter.adapter.ImageAdapter
import com.example.itcenter.databinding.FragmentHomeBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.ImageItem
import com.example.itcenter.model.viewmodel.MainViewModel
import java.util.UUID


class HomeFragment : Fragment() {
lateinit var binding: FragmentHomeBinding
 var handler = Handler()

    lateinit var pageChangeList: ViewPager2.OnPageChangeCallback
    private lateinit var imageChangeRunnable: Runnable
    private val handler2 = Handler(Looper.getMainLooper())
    lateinit var viewModel: MainViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }
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
//        val imageList = ArrayList<SlideModel>()
//
//        imageList.add(SlideModel("https://bit.ly/2YoJ77H",ScaleTypes.CENTER_CROP))
//        imageList.add(SlideModel("https://bit.ly/2BteuF2",ScaleTypes.CENTER_CROP))
//        imageList.add(SlideModel("https://bit.ly/3fLJf72", ScaleTypes.CENTER_CROP))
//
//        val imageSlider = requireActivity().findViewById<ImageSlider>(R.id.image_slider)
//        imageSlider.setImageList(imageList)

        val imageAdapter = ImageAdapter()
        binding.viewPager.adapter = imageAdapter
        viewModel.adsData.observe(requireActivity(), Observer {

            imageAdapter.submitList(it)
            val dotsImage = Array(it.size) { ImageView(requireContext()) }

            dotsImage.forEach {item->
                item.setImageResource(
                    R.drawable.non_active_dot
                )
                binding.slideDot.addView(item)
            }
            dotsImage[0].setImageResource(R.drawable.active_dot)


            imageChangeRunnable = object : Runnable {
                override fun run() {
                    val nextIndex = (binding.viewPager.currentItem + 1) % it.size
                    binding.viewPager.currentItem = nextIndex
                    handler2.postDelayed(this, 2000) // Change image every 3 seconds

                }
            }

            handler2.postDelayed(imageChangeRunnable, 3000)


            pageChangeList = object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    dotsImage.forEachIndexed { index, imageView ->
                        imageView.setImageResource(
                            if (position == index) R.drawable.active_dot else R.drawable.non_active_dot
                        )
                    }
                    super.onPageSelected(position)
                }
            }
        })




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
        binding.tvAll.setOnClickListener {
            startActivity(Intent(requireContext(),AllCategoryActivity::class.java))
        }
        val intent2 = Intent(requireContext(), AllStudentActivity::class.java)
        binding.tvAllAndroid.setOnClickListener {
            intent2.apply {
                putExtra("Group","Android")
            }
            startActivity(intent2)
        }
        binding.tvAllPython.setOnClickListener {
            intent2.apply {
                putExtra("Group","Python")
            }
            startActivity(intent2)
        }
        binding.tvAllScratch.setOnClickListener {
            intent2.apply {
                putExtra("Group","Scratch")
            }
            startActivity(intent2)
        }
        binding.tvAllLiteracy.setOnClickListener {
            intent2.apply {
                putExtra("Group","Literacy")
            }
            startActivity(intent2)
        }

        var android = arrayListOf<AllStudentModel>()
        var python = arrayListOf<AllStudentModel>()
        var scratch = arrayListOf<AllStudentModel>()
        var literacy = arrayListOf<AllStudentModel>()
        for (student in allStudentsList){
            if (student.language=="android"){
                android.add(student)
            }else if (student.language=="python"){
                python.add(student)
            }else if (student.language=="scratch"){
                scratch.add(student)
            }else if (student.language=="literacy"){
                literacy.add(student)
            }
        }
        loadData()

        val androidThread = Thread { topAndroid(android) }
        androidThread.start()
        val pythonThread = Thread { topPython(python) }
        pythonThread.start()
        val scratchThread = Thread { topScratch(scratch) }
        scratchThread.start()
        val literacyThread = Thread { topLiteracy(literacy) }
        literacyThread.start()
    }
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    fun  loadData(){
        viewModel.getOffers()
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
    private fun topPython(students: ArrayList<AllStudentModel>) {
        val top = students.sortedByDescending { it.foiz }.take(3)
        binding.imgPython1.setImageResource(top[0].img)
        binding.imgPython2.setImageResource(top[1].img)
        binding.imgPython3.setImageResource(top[2].img)
        binding.namePython1.text = top[0].name
        binding.namePython2.text = top[1].name
        binding.namePython3.text = top[2].name
        for (value in 0..top[0].foiz){
            try {
                Thread.sleep(10)
                binding.progressPython1.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizPython1.text = topAndroidtxt
            })
        }
        for (value in 0..top[1].foiz){
            try {
                Thread.sleep(10)
                binding.progressPython2.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizPython2.text = topAndroidtxt
            })
        }
        for (value in 0..top[2].foiz){
            try {
                Thread.sleep(10)
                binding.progressPython3.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizPython3.text = topAndroidtxt
            })
        }
    }
    private fun topScratch(students: ArrayList<AllStudentModel>) {
        val top = students.sortedByDescending { it.foiz }.take(3)
        binding.imgScratch1.setImageResource(top[0].img)
        binding.imgScratch2.setImageResource(top[1].img)
        binding.imgScratch3.setImageResource(top[2].img)
        binding.nameScratch1.text = top[0].name
        binding.nameScratch2.text = top[1].name
        binding.nameScratch3.text = top[2].name
        for (value in 0..top[0].foiz){
            try {
                Thread.sleep(10)
                binding.progressScratch1.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizScratch1.text = topAndroidtxt
            })
        }
        for (value in 0..top[1].foiz){
            try {
                Thread.sleep(10)
                binding.progressScratch2.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizScratch2.text = topAndroidtxt
            })
        }
        for (value in 0..top[2].foiz){
            try {
                Thread.sleep(10)
                binding.progressScratch3.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizScratch3.text = topAndroidtxt
            })
        }
    }
    private fun topLiteracy(students: ArrayList<AllStudentModel>) {
        val top = students.sortedByDescending { it.foiz }.take(3)
        binding.imgLiteracy1.setImageResource(top[0].img)
        binding.imgLiteracy2.setImageResource(top[1].img)
        binding.imgLiteracy3.setImageResource(top[2].img)
        binding.nameLiteracy1.text = top[0].name
        binding.nameLiteracy2.text = top[1].name
        binding.nameLiteracy3.text = top[2].name
        for (value in 0..top[0].foiz){
            try {
                Thread.sleep(10)
                binding.progressLiteracy1.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizLiteracy1.text = topAndroidtxt
            })
        }
        for (value in 0..top[1].foiz){
            try {
                Thread.sleep(10)
                binding.progressLiteracy2.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizLiteracy2.text = topAndroidtxt
            })
        }
        for (value in 0..top[2].foiz){
            try {
                Thread.sleep(10)
                binding.progressLiteracy3.progress = value
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var topAndroidtxt = value.toString()
            handler.post(Runnable {
                binding.foizLiteracy3.text = topAndroidtxt
            })
        }
    }
}