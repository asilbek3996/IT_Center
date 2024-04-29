package com.example.itcenter.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.itcenter.activity.DarslarActivity
import com.example.itcenter.databinding.CategoryItemLayoutBinding
import com.example.itcenter.model.CategoryModel

class CategoryAdapter(val items: List<CategoryModel>): RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: CategoryItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.caytegoryImg.setImageResource(item.img)
        holder.binding.categoryTxt.text = item.title
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context,DarslarActivity::class.java)
            intent.putExtra("Til",item.title)
            it.context.startActivity(intent)
        }
    }

}