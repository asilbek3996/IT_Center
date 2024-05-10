package com.example.homepage.fragments

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.example.itcenter.adapter.ImageAdapter
import com.example.itcenter.databinding.FragmentHomeBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.viewmodel.MainViewModel
import kotlin.math.abs


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var handler = Handler()

    lateinit var pageChangeList: ViewPager2.OnPageChangeCallback
    private lateinit var imageChangeRunnable: Runnable
    private val handler2 = Handler(Looper.getMainLooper())
    lateinit var viewModel: MainViewModel
    lateinit var refresh: ImageView
    private var isItemVisible = false

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
        val intent = Intent(requireContext(), DarslarActivity::class.java)
        binding.category1.setOnClickListener {
            intent.apply {
                putExtra("Til", "Kotlin")
            }
            startActivity(intent)
        }
        binding.category2.setOnClickListener {
            intent.apply {
                putExtra("Til", "Java")
            }
            startActivity(intent)
        }
        binding.category3.setOnClickListener {
            intent.apply {
                putExtra("Til", "Python")
            }
            startActivity(intent)
        }

        val intent2 = Intent(requireContext(), AllStudentActivity::class.java)
        binding.tvAllAndroid.setOnClickListener {
            intent2.apply {
                putExtra("Group", "Java, Kotlin")
            }
            startActivity(intent2)
        }
        binding.tvAllPython.setOnClickListener {
            intent2.apply {
                putExtra("Group", "Python, C++")
            }
            startActivity(intent2)
        }
        binding.tvAllScratch.setOnClickListener {
            intent2.apply {
                putExtra("Group", "Scratch")
            }
            startActivity(intent2)
        }
        binding.tvAllLiteracy.setOnClickListener {
            intent2.apply {
                putExtra("Group", "Literacy")
            }
            startActivity(intent2)
        }
        viewModel.studentData.observe(requireActivity(), Observer {
            topAndroid(it)
            topPython(it)
            topScratch(it)
            topLiteracy(it)
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

    private fun categories(category: List<CategoryModel>) {
        val top = category.sortedBy { it.id }.take(3)
        Glide.with(binding.imgCategory1).load(top[0].image).into(binding.imgCategory1)
        Glide.with(binding.imgCategory2).load(top[1].image).into(binding.imgCategory2)
        Glide.with(binding.imgCategory3).load(top[2].image).into(binding.imgCategory3)
        binding.nameCategory1.text = top[0].language
        binding.nameCategory2.text = top[1].language
        binding.nameCategory3.text = top[2].language
    }

    private fun topAndroid(students: ArrayList<AllStudentModel>) {
        val intent = Intent(requireContext(), AboutStudentActivity::class.java)
        var android = arrayListOf<AllStudentModel>()
        for (student in students) {
            if (student.group == "Java, Kotlin") {
                android.add(student)
            }
        }

        val top = android.sortedByDescending { it.userPercentage }
        if (android.isNotEmpty()) {
            binding.androidNotStudents.visibility = View.GONE
            if (android.size in 1..1) {
                top.take(1)
                binding.android3.visibility = View.INVISIBLE
                binding.android2.visibility = View.INVISIBLE
                binding.android1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                Glide.with(binding.imgAndroid1).load(top[0].userPhoto).into(binding.imgAndroid1)
                binding.nameAndroid1.text = top[0].fullName
                for (value in 0..top[0].userPercentage) {
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
            } else if (android.size in 1..2) {
                top.take(2)
                binding.android3.visibility = View.INVISIBLE
                binding.android1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.android2.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[1].fullName)
                    }
                    startActivity(intent)
                }
                Glide.with(binding.imgAndroid1).load(top[0].userPhoto).into(binding.imgAndroid1)
                Glide.with(binding.imgAndroid2).load(top[1].userPhoto).into(binding.imgAndroid2)
                binding.nameAndroid1.text = top[0].fullName
                binding.nameAndroid2.text = top[1].fullName
                for (value in 0..top[0].userPercentage) {
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
                for (value in 0..top[1].userPercentage) {
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
            } else if (android.size > 0 && android.size > 2) {
                top.take(3)
                binding.android1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.android2.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[1].fullName)
                    }
                    startActivity(intent)
                }
                binding.android3.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[2].fullName)
                    }
                    startActivity(intent)
                }
                Glide.with(binding.imgAndroid1).load(top[0].userPhoto).into(binding.imgAndroid1)
                Glide.with(binding.imgAndroid2).load(top[1].userPhoto).into(binding.imgAndroid2)
                Glide.with(binding.imgAndroid3).load(top[2].userPhoto).into(binding.imgAndroid3)
                binding.nameAndroid1.text = top[0].fullName
                binding.nameAndroid2.text = top[1].fullName
                binding.nameAndroid3.text = top[2].fullName
                for (value in 0..top[0].userPercentage) {
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
                for (value in 0..top[1].userPercentage) {
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
                for (value in 0..top[2].userPercentage) {
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
        } else {
            binding.androidNotStudents.visibility = View.GONE
        }
    }

    private fun topPython(students: ArrayList<AllStudentModel>) {
        val intent = Intent(requireContext(), AboutStudentActivity::class.java)
        var python = arrayListOf<AllStudentModel>()
        for (student in students) {
            if (student.group == "Python, C++") {
                python.add(student)
            }
        }
        val top = python.sortedByDescending { it.userPercentage }
        if (python.isNotEmpty()) {
            binding.pythonNotStudents.visibility = View.GONE
            if (python.size in 1..1) {
                top.take(1)
                binding.python1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.python3.visibility = View.INVISIBLE
                binding.python2.visibility = View.INVISIBLE
                Glide.with(binding.imgPython1).load(top[0].userPhoto).into(binding.imgPython1)
                binding.namePython1.text = top[0].fullName
                for (value in 0..top[0].userPercentage) {
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
            } else if (python.size in 1..2) {
                top.take(2)
                binding.python1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.python2.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[1].fullName)
                    }
                    startActivity(intent)
                }
                binding.python3.visibility = View.INVISIBLE
                Glide.with(binding.imgPython1).load(top[0].userPhoto).into(binding.imgPython1)
                Glide.with(binding.imgPython2).load(top[1].userPhoto).into(binding.imgPython2)
                binding.namePython1.text = top[0].fullName
                binding.namePython2.text = top[1].fullName
                for (value in 0..top[0].userPercentage) {
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
                for (value in 0..top[1].userPercentage) {
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
            } else if (python.size > 0 && python.size > 2) {
                top.take(3)
                binding.python1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.python2.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[1].fullName)
                    }
                    startActivity(intent)
                }
                binding.python3.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[2].fullName)
                    }
                    startActivity(intent)
                }
                Glide.with(binding.imgPython1).load(top[0].userPhoto).into(binding.imgPython1)
                Glide.with(binding.imgPython2).load(top[1].userPhoto).into(binding.imgPython2)
                Glide.with(binding.imgPython3).load(top[2].userPhoto).into(binding.imgPython3)
                binding.namePython1.text = top[0].fullName
                binding.namePython2.text = top[1].fullName
                binding.namePython3.text = top[2].fullName
                for (value in 0..top[0].userPercentage) {
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
                for (value in 0..top[1].userPercentage) {
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
                for (value in 0..top[2].userPercentage) {
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
        } else {
            binding.pythonStudents.visibility = View.GONE
        }
    }
    private fun topScratch(students: ArrayList<AllStudentModel>) {
        val intent = Intent(requireContext(), AboutStudentActivity::class.java)
        var scratch = arrayListOf<AllStudentModel>()
        for (student in students) {
            if (student.group == "Scratch") {
                scratch.add(student)
            }
        }
        val top = scratch.sortedByDescending { it.userPercentage }
        if (scratch.isNotEmpty()) {
            binding.scratchNotStudents.visibility = View.GONE
            binding.scratchStudents.visibility = View.VISIBLE
            if (scratch.size in 1..1) {
                top.take(1)
                binding.scratch1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.scratch3.visibility = View.INVISIBLE
                binding.scratch2.visibility = View.INVISIBLE
                Glide.with(binding.imgScratch1).load(top[0].userPhoto).into(binding.imgScratch1)
                binding.nameScratch1.text = top[0].fullName
                for (value in 0..top[0].userPercentage) {
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
            } else if (scratch.size in 1..2) {
                top.take(2)
                binding.scratch1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.scratch2.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[1].fullName)
                    }
                    startActivity(intent)
                }
                binding.scratch3.visibility = View.INVISIBLE
                Glide.with(binding.imgScratch1).load(top[0].userPhoto).into(binding.imgScratch1)
                Glide.with(binding.imgScratch2).load(top[1].userPhoto).into(binding.imgScratch2)
                binding.nameScratch1.text = top[0].fullName
                binding.nameScratch2.text = top[1].fullName
                for (value in 0..top[0].userPercentage) {
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
                for (value in 0..top[1].userPercentage) {
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
            } else if (scratch.size > 0 && scratch.size > 2) {
                top.take(3)
                binding.scratch1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.scratch2.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[1].fullName)
                    }
                    startActivity(intent)
                }
                binding.scratch3.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[2].fullName)
                    }
                    startActivity(intent)
                }
                Glide.with(binding.imgScratch1).load(top[0].userPhoto).into(binding.imgScratch1)
                Glide.with(binding.imgScratch2).load(top[1].userPhoto).into(binding.imgScratch2)
                Glide.with(binding.imgScratch3).load(top[2].userPhoto).into(binding.imgScratch3)
                binding.nameScratch1.text = top[0].fullName
                binding.nameScratch2.text = top[1].fullName
                binding.nameScratch3.text = top[2].fullName
                for (value in 0..top[0].userPercentage) {
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
                for (value in 0..top[1].userPercentage) {
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
                for (value in 0..top[2].userPercentage) {
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
        } else {
            binding.scratchStudents.visibility = View.GONE
            binding.scratchNotStudents.visibility = View.VISIBLE
        }
    }

    private fun topLiteracy(students: ArrayList<AllStudentModel>) {
        val intent = Intent(requireContext(), AboutStudentActivity::class.java)
        var literacy = arrayListOf<AllStudentModel>()
        for (student in students) {
            if (student.group == "Literacy") {
                literacy.add(student)
            }
        }
        val top = literacy.sortedByDescending { it.userPercentage }
        if (literacy.isNotEmpty()) {
            binding.literacyNotStudents.visibility = View.GONE
            if (literacy.size in 1..1) {
                top.take(1)
                binding.literacy1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.scratch3.visibility = View.INVISIBLE
                binding.scratch2.visibility = View.INVISIBLE
                Glide.with(binding.imgLiteracy1).load(top[0].userPhoto).into(binding.imgLiteracy1)
                binding.nameLiteracy1.text = top[0].fullName
                for (value in 0..top[0].userPercentage) {
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
            } else if (literacy.size in 1..2) {
                top.take(2)
                binding.literacy1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.literacy2.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[1].fullName)
                    }
                    startActivity(intent)
                }
                Glide.with(binding.imgLiteracy1).load(top[0].userPhoto).into(binding.imgLiteracy1)
                Glide.with(binding.imgLiteracy2).load(top[1].userPhoto).into(binding.imgLiteracy2)
                binding.nameLiteracy1.text = top[0].fullName
                binding.nameLiteracy2.text = top[1].fullName
                for (value in 0..top[0].userPercentage) {
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
                for (value in 0..top[1].userPercentage) {
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
            } else if (literacy.size > 0 && literacy.size > 2) {
                top.take(3)
                binding.literacy1.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[0].fullName)
                    }
                    startActivity(intent)
                }
                binding.literacy2.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[1].fullName)
                    }
                    startActivity(intent)
                }
                binding.literacy3.setOnClickListener {
                    intent.apply {
                        putExtra("Student", top[2].fullName)
                    }
                    startActivity(intent)
                }
                binding.scratch3.visibility = View.INVISIBLE
                Glide.with(binding.imgLiteracy1).load(top[0].userPhoto).into(binding.imgLiteracy1)
                Glide.with(binding.imgLiteracy2).load(top[1].userPhoto).into(binding.imgLiteracy2)
                Glide.with(binding.imgLiteracy3).load(top[2].userPhoto).into(binding.imgLiteracy3)
                binding.nameLiteracy1.text = top[0].fullName
                binding.nameLiteracy2.text = top[1].fullName
                binding.nameLiteracy3.text = top[2].fullName
                for (value in 0..top[0].userPercentage) {
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
                for (value in 0..top[1].userPercentage) {
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
                for (value in 0..top[2].userPercentage) {
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
        } else {
            binding.literacyStudents.visibility = View.GONE
        }
    }

}