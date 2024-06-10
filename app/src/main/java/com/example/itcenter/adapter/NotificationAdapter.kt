package com.example.itcenter.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.itcenter.R
import com.example.itcenter.activity.AllStudentActivity
import com.example.itcenter.activity.NotificationDetailActivity
import com.example.itcenter.databinding.NotificationItemLayoutBinding
import com.example.itcenter.databinding.ViewholderQuestionBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.Notification

class NotificationAdapter(var items: ArrayList<AllStudentModel>) : RecyclerView.Adapter<NotificationAdapter.Viewholder>() {
    private lateinit var binding: NotificationItemLayoutBinding
    inner class Viewholder : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflate = LayoutInflater.from(parent.context)
        binding = NotificationItemLayoutBinding.inflate(inflate, parent, false)
        return Viewholder()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var item = items[position]
        binding.notificationTxt.text = item.fullName
        val intent = Intent(holder.itemView.context, NotificationDetailActivity::class.java)
        holder.itemView.setOnClickListener {
            intent.getIntExtra("notification",item.id)
            it.context.startActivity(intent)
        }
    }

}