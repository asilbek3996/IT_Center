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
import com.orhanobut.hawk.Hawk

class NotificationDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNotificationDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var message = intent.getIntExtra("notification",-1)
//        saveQuestionsToCache(message)
        var a = a()
        Toast.makeText(this, "${a.size}", Toast.LENGTH_SHORT).show()
    }
//    private fun saveQuestionsToCache(id: Int) {
//        var notification = allNotification()
//        var a = a()
//        var notificationRead = notification.filter { it.id == id }
//        a.add(notificationRead)
//        Hawk.put("read_notification", a)
//    }

    private fun allNotification(): ArrayList<AllStudentModel> {
        return Hawk.get("cached_questions", ArrayList())
    }
    private fun a(): ArrayList<AllStudentModel> {
        return Hawk.get("read_notification", ArrayList())
    }
}