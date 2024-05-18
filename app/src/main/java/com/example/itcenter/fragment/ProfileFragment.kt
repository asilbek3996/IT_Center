package com.example.itcenter.fragment

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
import com.example.itcenter.ShowProgress
import com.example.itcenter.activity.CheckActivity
import com.example.itcenter.activity.SettingsActivity
import com.example.itcenter.databinding.FragmentProfileBinding
import com.example.itcenter.model.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: MainViewModel
    lateinit var refresh: ImageView
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
        refresh = requireActivity().findViewById(R.id.refresh)
        refresh.setOnClickListener {
            binding.swipe.isRefreshing = true
            loadData()
            (activity as? ShowProgress.View)?.showProgressBar()
        }
        binding.swipe.setOnRefreshListener {
            loadData()
        }
        loadData()
        viewModel.progress.observe(requireActivity()) {
            binding.swipe.isRefreshing = it
            if (it) {
                (activity as? ShowProgress.View)?.showProgressBar()
            } else {
                (activity as? ShowProgress.View)?.hideProgressBar()
            }
        }
        viewModel.studentData.observe(requireActivity()){
            for (item in it){
                if (item.id == idRaqami){
                    Glide.with(requireActivity()).load(item.userPhoto).into(binding.img)
                    binding.tvFullName.text = item.fullName
                    binding.tvID.text = item.id.toString()
                }
            }
        }
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
fun loadData(){
    viewModel.getStudent()
}
    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }
    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}