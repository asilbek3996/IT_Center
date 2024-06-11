package com.example.itcenter.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityNotificationDetailBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.model.Notification
import com.example.itcenter.utils.Constants
import com.orhanobut.hawk.Hawk

class NotificationDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNotificationDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var message = intent.getIntExtra("notification",-1)
        Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
        saveQuestionsToCache(message)
    }
    private fun saveQuestionsToCache(id: Int) {
        var notification = allNotification()
        Toast.makeText(this, "${id}", Toast.LENGTH_SHORT).show()
        for (item in notification) {
            if (item.id == id) {
                item.isRead = true
                Toast.makeText(this, "${item.id}", Toast.LENGTH_SHORT).show()
                break
            }
        }
        Hawk.put(Constants.notification, notification)
    }

    private fun allNotification(): ArrayList<Notification> {
        return Hawk.get(Constants.notification, ArrayList())
    }
}