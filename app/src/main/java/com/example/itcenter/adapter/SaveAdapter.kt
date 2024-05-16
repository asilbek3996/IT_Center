package com.example.itcenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itcenter.R
import com.example.itcenter.model.DarslarModel

class SaveAdapter(private val items: List<DarslarModel>) : RecyclerView.Adapter<SaveAdapter.SaveViewHolder>() {

    inner class SaveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder ichidagi UI elementlar
        // Masalan, ImageView, TextView, va boshqa kerakli elementlar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.save_item, parent, false)
        return SaveViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaveViewHolder, position: Int) {
        val currentItem = items[position]
        // Holderdagi elementlarni currentItem ma'lumotlari bilan to'ldiring
    }

    override fun getItemCount(): Int {
        // RecyclerView ichidagi elementlar sonini qaytarish
        // Masalan, o'qilgan ma'lumotlar soni
        return items.size
    }
}
