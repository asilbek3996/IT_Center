package com.example.itcenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itcenter.activity.DarslarActivity
import com.example.itcenter.databinding.AlStudentItemLayoutBinding
import com.example.itcenter.databinding.DarslarBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.DarslarModel

class StudentAdapter(val items: List<AllStudentModel>): RecyclerView.Adapter<StudentAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: AlStudentItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(AlStudentItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.binding.img)
            .load(item.userPhoto)
            .into(holder.binding.img)
        holder.binding.name.text = item.fullName
        holder.binding.progress.progress = item.userPercentage
        var txt = item.userPercentage
        holder.binding.foiz.text = txt.toString()

    }

}