package com.example.itcenter.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itcenter.R
import com.example.itcenter.databinding.GroupStudentBinding
import com.example.itcenter.databinding.TopStudentItemLayouBinding
import com.example.itcenter.model.AllStudentModel

class StudentsGroupAdapter(private val students: List<AllStudentModel>) :
    RecyclerView.Adapter<StudentsGroupAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: GroupStudentBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(GroupStudentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val student = students[position]
        holder.binding.progress1.progress = student.userPercentage
        holder.binding.foiz1.text = student.userPercentage.toString()
        holder.binding.name1.text = student.fullName
        Glide.with(holder.binding.img1).load(student.userPhoto).into(holder.binding.img1)
    }
    override fun getItemCount(): Int {
        return students.size
    }
}