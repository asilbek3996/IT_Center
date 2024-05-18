package com.example.itcenter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itcenter.PrefUtils
import com.example.itcenter.adapter.SaveAdapter
import com.example.itcenter.databinding.FragmentFavoriteBinding
import com.example.itcenter.model.DarslarModel
import com.example.itcenter.model.viewmodel.MainViewModel

class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: MainViewModel
    private lateinit var adapter: SaveAdapter
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
        var items= arrayListOf<DarslarModel>()
        viewModel.lessonsData.observe(requireActivity()){
            val intArrayPrefs = PrefUtils(requireContext())
            val intArray = intArrayPrefs.getIntArray("f")
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
                adapter = SaveAdapter(items) // Adapter o'rnating
                binding.favoriteRecyclerView.adapter = adapter // Adapterni RecyclerViewga ulang
            }
        }

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