package com.example.itcenter.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.itcenter.R
import com.example.itcenter.activity.VideoActivity
import com.example.itcenter.model.DarslarModel
import com.example.itcenter.utils.Constants
import com.example.itcenter.utils.PrefUtils

interface ItemClickedListener {
    fun onItemClicked(position: Int)
}
class SaveAdapter(private val items: List<DarslarModel>, private val fragment: Fragment, private val listener: ItemClickedListener) : RecyclerView.Adapter<SaveAdapter.SaveViewHolder>() {

    inner class SaveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder ichidagi UI elementlar
        // Masalan, ImageView, TextView, va boshqa kerakli elementlar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.save_item, parent, false)
        return SaveViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaveViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.findViewById<TextView>(R.id.titleText).text = item.lessonName
        holder.itemView.findViewById<ImageView>(R.id.delete).setOnClickListener {
            listener.onItemClicked(item.id)
        }
        holder.itemView.setOnClickListener {
                val intent = Intent(it.context, VideoActivity::class.java)
                intent.putExtra("video", item.id)
                it.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            Toast.makeText(fragment.requireContext(), item.lessonName, Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun getItemCount(): Int {
        // RecyclerView ichidagi elementlar sonini qaytarish
        // Masalan, o'qilgan ma'lumotlar soni
        return items.size
    }
}
