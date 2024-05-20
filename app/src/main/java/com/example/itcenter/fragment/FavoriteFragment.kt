package com.example.itcenter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itcenter.R
import com.example.itcenter.ShowProgress
import com.example.itcenter.adapter.ItemClickedListener
import com.example.itcenter.utils.PrefUtils
import com.example.itcenter.adapter.SaveAdapter
import com.example.itcenter.databinding.FragmentFavoriteBinding
import com.example.itcenter.model.DarslarModel
import com.example.itcenter.model.viewmodel.MainViewModel
import com.example.itcenter.utils.Constants

class FavoriteFragment : Fragment(), ItemClickedListener {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: MainViewModel
    private lateinit var adapter: SaveAdapter
    lateinit var refresh: ImageView
    var items= arrayListOf<DarslarModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        viewModel.lessonsData.observe(requireActivity()){
            val intArrayPrefs = PrefUtils(requireContext())
            val intArray = intArrayPrefs.getFavorite(Constants.favorite)
            intArray?.let {item->
                for (num in item) {
                    for (i in it){
                        if (i.id==num){
                            items.add(i)

                        }
                    }
                }
                Toast.makeText(requireContext(), "${items.size}", Toast.LENGTH_SHORT).show()
                binding.favoriteRecyclerView.layoutManager = GridLayoutManager(requireActivity(),3)
                adapter = SaveAdapter(items,this,this)
                binding.favoriteRecyclerView.adapter = adapter
            }
        }



        refresh = requireActivity().findViewById(R.id.refresh)
        refresh.setOnClickListener {
            items.clear()
            binding.swipe.isRefreshing = true
            loadData()
            (activity as? ShowProgress.View)?.showProgressBar()
        }
        binding.swipe.setOnRefreshListener {
            loadData()
            items.clear()
        }
        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = it
            if (it) {
                (activity as? ShowProgress.View)?.showProgressBar()
            } else {
                (activity as? ShowProgress.View)?.hideProgressBar()
            }
        })

    }
    override fun onItemClicked(position: Int) {
        val pref = PrefUtils(requireContext())
        pref.saveFavorite(Constants.favorite,position)
        loadData()
        items.clear()
    }
    fun loadData(){
        viewModel.getLessons()
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }
    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }

}