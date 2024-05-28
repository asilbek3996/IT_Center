package com.example.itcenter.fragment

import android.content.ActivityNotFoundException
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
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.viewmodel.MainViewModel
import com.example.itcenter.utils.Constants
import com.example.itcenter.utils.PrefUtils
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
        val pref = PrefUtils(requireContext())
        var idRaqami = pref.getID()
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
        viewModel.progress.observe(requireActivity()) {
            binding.swipe.isRefreshing = it
            if (it) {
                (activity as? ShowProgress.View)?.showProgressBar()
            } else {
                (activity as? ShowProgress.View)?.hideProgressBar()
            }
        }
        loadData()
        var idRaqam = pref.getID()
        viewModel.studentData.observe(requireActivity()){
            for (items in it){
                if (items.id == idRaqami){
                    if (items.id == idRaqami){
                        Glide.with(binding.img).load(items.userPhoto).into(binding.img)
                        binding.tvFullName.text = items.fullName
                        binding.tvID.text = idRaqami.toString()
                    }
                }else{
                    pref.clear()
                    startActivity(Intent(requireActivity(), CheckActivity::class.java))
                    requireActivity().finish()
                }
            }
        }

        binding.rating.setOnClickListener {
            val uri: Uri =Uri.parse("market://details?id=com.google.android.youtube")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)

            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            try {
                startActivity(goToMarket)
            }catch (e: ActivityNotFoundException){
                startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube")))
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
                pref.clear()
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