package com.example.itcenter.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itcenter.activity.AboutStudentActivity
import com.example.itcenter.activity.VideoActivity
import com.example.itcenter.databinding.DarslarBinding
import com.example.itcenter.model.DarslarModel

class DarsAdapter(val items: List<DarslarModel>): RecyclerView.Adapter<DarsAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: DarslarBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(DarslarBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.lessonName
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, VideoActivity::class.java)
            intent.putExtra("video", item)
            it.context.startActivity(intent)
        }
    }
}