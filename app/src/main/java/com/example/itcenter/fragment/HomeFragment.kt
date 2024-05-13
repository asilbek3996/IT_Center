package com.example.homepage.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.itcenter.R
import com.example.itcenter.ShowProgress
import com.example.itcenter.activity.AboutStudentActivity
import com.example.itcenter.activity.AllCategoryActivity
import com.example.itcenter.activity.AllStudentActivity
import com.example.itcenter.activity.DarslarActivity
import com.example.itcenter.activity.LevelActivity
import com.example.itcenter.adapter.ImageAdapter
import com.example.itcenter.adapter.TopStudentAdapter
import com.example.itcenter.databinding.FragmentHomeBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.GroupModel
import com.example.itcenter.model.viewmodel.MainViewModel
import kotlin.math.abs


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var handler = Handler()
    lateinit var viewModel: MainViewModel
    lateinit var refresh: ImageView
    var item = arrayListOf<CategoryModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setUpTransformer()
        viewModel.adsData.observe(requireActivity(), Observer {
            binding.viewPager.adapter = ImageAdapter(it, binding.viewPager)
            binding.viewPager.offscreenPageLimit = 3
            binding.viewPager.clipToPadding = false
            binding.viewPager.clipChildren = false
            binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        })
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })
        viewModel.categoriesData.observe(requireActivity(), Observer {
            categories(it)
        })
        refresh = requireActivity().findViewById(R.id.refresh)
        refresh.setOnClickListener {
            binding.swipe.isRefreshing = true
            loadData()
            (activity as? ShowProgress.View)?.showProgressBar()
        }
        binding.swipe.setOnRefreshListener {
            loadData()
        }
        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = it
            if (it) {
                (activity as? ShowProgress.View)?.showProgressBar()
            } else {
                (activity as? ShowProgress.View)?.hideProgressBar()
            }
        })

        binding.tvAll.setOnClickListener {
            startActivity(Intent(requireContext(), AllCategoryActivity::class.java))
        }
        val intent = Intent(requireContext(), LevelActivity::class.java)
        binding.category1.setOnClickListener {
            intent.apply {
                putExtra("Til", item[0].language)
            }
            startActivity(intent)
        }
        binding.category2.setOnClickListener {
            intent.apply {
                putExtra("Til", item[1].language)
            }
            startActivity(intent)
        }
        binding.category3.setOnClickListener {
            intent.apply {
                putExtra("Til", item[2].language)
            }
            startActivity(intent)
        }
        viewModel.studentData.observe(requireActivity(), Observer {
            topTest(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable, 3000)
    }

    private val runnable = Runnable {
        binding.viewPager.currentItem += 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(0))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        binding.viewPager.setPageTransformer(transformer)
    }

    fun loadData() {
        viewModel.getOffers()
        viewModel.getCategoris()
        viewModel.getStudent()
    }
    private fun topTest(students: ArrayList<AllStudentModel>) {
        var android = arrayListOf<AllStudentModel>()
        var python = arrayListOf<AllStudentModel>()
        var java = arrayListOf<AllStudentModel>()
        var kotlin = arrayListOf<AllStudentModel>()
        var cpp = arrayListOf<AllStudentModel>()
        var literacy = arrayListOf<AllStudentModel>()
        var scratch = arrayListOf<AllStudentModel>()
        var frontend = arrayListOf<AllStudentModel>()
        var student2 = ""

        val sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val idRaqami = sharedPreferences.getInt("id", -1)
        val editor = sharedPreferences.edit()
        for (student in students) {
            if (student.group== "Android") {
                android.add(student)
            }else if (student.group== "Python") {
                python.add(student)
            }else if (student.group== "Kotlin") {
                kotlin.add(student)
            }else if (student.group== "Java") {
                java.add(student)
            }else if (student.group== "C++") {
                cpp.add(student)
            }else if (student.group== "Scratch") {
                scratch.add(student)
            }else if (student.group== "Literacy") {
                literacy.add(student)
            }else if (student.group== "Frontend") {
                frontend.add(student)
            }
            if (student.id == idRaqami){
                student2 = student.group
                editor.putString("group", student.group)
                editor.putString("fullName", student.fullName)
                editor.apply()
            }

        }
        var group = arrayListOf<GroupModel>()
        var item = arrayListOf<GroupModel>()
        var items = listOf(
            GroupModel("Java",java),
            GroupModel("Kotlin",kotlin),
            GroupModel("Android",android),
            GroupModel("Python",python),
            GroupModel("C++",cpp),
            GroupModel("Literacy",literacy),
            GroupModel("Scratch",scratch),
            GroupModel("Frontend",frontend)
        )
        for (it in items){
            if (it.name == student2){
                group.add(it)
            }else{
                item.add(it)
            }
        }
        group.addAll(item)
        binding.recyclerGroup.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.recyclerGroup.adapter = TopStudentAdapter(group)
    }
    private fun categories(category: List<CategoryModel>) {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val group = sharedPreferences.getString("group", null)
        var item2 = arrayListOf<CategoryModel>()
        for (it in category){
            if (it.language == group){
                item.add(it)
            }else{
                item2.add(it)
            }
        }
        item.addAll(item2)
        Glide.with(binding.imgCategory1).load(item[0].image).into(binding.imgCategory1)
        Glide.with(binding.imgCategory2).load(item[1].image).into(binding.imgCategory2)
        Glide.with(binding.imgCategory3).load(item[2].image).into(binding.imgCategory3)
        binding.nameCategory1.text = item[0].language
        binding.nameCategory2.text = item[1].language
        binding.nameCategory3.text = item[2].language
    }
}