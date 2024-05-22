package com.example.itcenter.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itcenter.activity.DarslarActivity
import com.example.itcenter.activity.LevelActivity
import com.example.itcenter.databinding.AllCategoryItemLayoutBinding
import com.example.itcenter.databinding.CategoryItemLayoutBinding
import com.example.itcenter.databinding.QuizItemLayoutBinding
import com.example.itcenter.databinding.QuizLevelItemLayoutBinding
import com.example.itcenter.model.AllCategoryModel
import com.example.itcenter.model.CategoryModel
import com.example.itcenter.model.QuizLevelModel

class QuizLevelAdapter(var items: List<QuizLevelModel>): RecyclerView.Adapter<QuizLevelAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: QuizLevelItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            QuizLevelItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.tvMain.text = item.text
//        holder.itemView.setOnClickListener {
//            val intent = Intent(it.context, LevelActivity::class.java)
//            intent.putExtra("Language", item.language)
//            it.context.startActivity(intent)
//        }
    }
}