package com.example.itcenter.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityNotificationsBinding
import com.example.itcenter.model.AllStudentModel
import com.orhanobut.hawk.Hawk

class NotificationsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNotificationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun saveQuestionsToCache(questions: AllStudentModel) {
        var notification = allNotification()
        Hawk.put("cached_questions", questions)
    }

    private fun allNotification(): List<AllStudentModel> {
        return Hawk.get("cached_questions", emptyList())
    }
}