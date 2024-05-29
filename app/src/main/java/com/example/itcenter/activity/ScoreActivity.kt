package com.example.itcenter.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.itcenter.databinding.ActivityScoreBinding
import com.example.itcenter.model.QuestionModel
import com.example.itcenter.utils.PrefUtils

class ScoreActivity : AppCompatActivity() {
    var score: Int = 0
    lateinit var binding: ActivityScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        score=intent.getIntExtra("Score",0)
        var right=intent.getIntExtra("right",0)
        var wrong=intent.getIntExtra("wrong",0)
        var level1=intent.getIntExtra("level",1)
        binding.totalRight.text = right.toString()
        binding.totalWrong.text = wrong.toString()
        val txt = "${score}%"
        binding.score.text=txt
        var pref = PrefUtils(this)
        var level = pref.getQuizLevel()
        if (score==100){
            level+=1
        }
        pref.setQuizLevel(level)
        binding.backMain.setOnClickListener {
                startActivity(Intent(this@ScoreActivity,QuizStartActivity::class.java))
                finish()
        }
        binding.refresh.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("list",level1)
            it.context.startActivity(intent)
        }
    }

}