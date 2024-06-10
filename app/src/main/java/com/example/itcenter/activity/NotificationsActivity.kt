package com.example.itcenter.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itcenter.R
import com.example.itcenter.adapter.NotificationAdapter
import com.example.itcenter.databinding.ActivityNotificationsBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.Notification
import com.example.itcenter.utils.Constants
import com.orhanobut.hawk.Hawk

class NotificationsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNotificationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.notificationRecycler.layoutManager = LinearLayoutManager(this)
        binding.notificationRecycler.adapter = NotificationAdapter(allNotification())
    }

    private fun saveNotification(questions: Notification) {
        var notification = allNotification()
        Hawk.put(Constants.notification, questions)
    }

    private fun allNotification(): ArrayList<AllStudentModel> {
        return Hawk.get(Constants.notification, ArrayList())
    }
}