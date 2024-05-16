package com.example.homepage.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itcenter.R
import com.example.itcenter.adapter.SaveAdapter
import com.example.itcenter.databinding.FragmentFavoriteBinding
import com.example.itcenter.model.DarslarModel
import com.example.itcenter.model.viewmodel.MainViewModel

class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: MainViewModel
    private lateinit var adapter: SaveAdapter
    private lateinit var recyclerView: RecyclerView
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
        val sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        var id = sharedPreferences.getInt("favorite",0)
        var items= arrayListOf<DarslarModel>()
        viewModel.lessonsData.observe(requireActivity()) {
            for (i in it) {
                if (i.id == id) {
                    items.add(i)
                }
            }
        }
        recyclerView = view.findViewById(R.id.favorite_recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireActivity(),3)
        adapter = SaveAdapter(items) // Adapter o'rnating
        recyclerView.adapter = adapter // Adapterni RecyclerViewga ulang
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}