package com.example.homepage.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.itcenter.R
import com.example.itcenter.activity.CheckActivity
import com.example.itcenter.databinding.FragmentProfileBinding
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val idRaqami = sharedPreferences.getInt("id", -1)
        val firebaseAuth = FirebaseAuth.getInstance()
        binding.logOut.setOnClickListener {
            if (idRaqami == -1){
                firebaseAuth.signOut()
            }else{
                editor.clear()
                editor.apply()
            }
            startActivity(Intent(requireActivity(),CheckActivity::class.java))
            requireActivity().finish()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}