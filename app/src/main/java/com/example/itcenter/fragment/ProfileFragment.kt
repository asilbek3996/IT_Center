package com.example.homepage.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.itcenter.R
import com.example.itcenter.activity.CheckActivity
import com.example.itcenter.activity.SettingsActivity
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
        binding.settings.setOnClickListener {
            startActivity(Intent(requireActivity(),SettingsActivity::class.java))
        }
        viewModel.userData.observe(requireActivity()){
                    Glide.with(requireActivity()).load(it.userPhoto).into(binding.img)
                    binding.tvFullName.text = it.fullName
                    binding.tvID.text = it.id.toString()
        }
        viewModel.getUser(idRaqami)
        binding.support.setOnClickListener {
            val link = "https://t.me/dangara_itcenterbot" // Sizning linkingizni o'zgartiring
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
            requireActivity().finish()
        }
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