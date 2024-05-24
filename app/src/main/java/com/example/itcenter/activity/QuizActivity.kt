package com.example.itcenter.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.itcenter.R
import com.example.itcenter.adapter.QuestionAdapter
import com.example.itcenter.adapter.score
import com.example.itcenter.databinding.ActivityQuizBinding
import com.example.itcenter.model.QuestionModel
import com.example.itcenter.utils.Constants
import com.example.itcenter.utils.PrefUtils

class QuizActivity : AppCompatActivity(), score {
    private lateinit var binding: ActivityQuizBinding
    var position: Int = 0
    var receivedList: MutableList<QuestionModel> = mutableListOf()
    var allScore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = this@QuizActivity.window
        window.statusBarColor = ContextCompat.getColor(this@QuizActivity, R.color.grey)


        receivedList = intent.getParcelableArrayListExtra<QuestionModel>("list")!!.toMutableList()
        var count = receivedList.size
        var pref = PrefUtils(this)
        var name = pref.getStudent(Constants.fullName)
        var txt = "Hi $name"
        binding.tvMain.text = txt
        binding.back.setOnClickListener {
            startActivity(Intent(this,LevelActivity::class.java))
            finish()
        }
        binding.apply {

            questionNumberTxt.text = "Question 1/$count"
            progressBar.progress = 1

            questionTxt.text = receivedList[position].question


            loadAnswers()
            progressBar.max = count
            rightArrow.setOnClickListener {
                if (progressBar.progress == count) {
                    val intent = Intent(this@QuizActivity, ScoreActivity::class.java)
                    intent.putExtra("Score", allScore)
                    startActivity(intent)
                    finish()
                    return@setOnClickListener
                }
                position++
                progressBar.progress = progressBar.progress + 1
                binding.questionNumberTxt.text = "Question" + binding.progressBar.progress + "/$count"
                questionTxt.text=receivedList[position].question


                loadAnswers()

            }

        }
    }

    override fun amount(number: Int, clickedAnswer: String) {
        allScore += number
        receivedList[position].clickedAnswer = clickedAnswer
        var count = receivedList.size
        Handler().postDelayed({
            if (binding.progressBar.progress == count) {
                val intent = Intent(this@QuizActivity, ScoreActivity::class.java)
                intent.putExtra("Score", allScore)
                startActivity(intent)
                finish()
            }
            var count = receivedList.size
            position++
            binding.progressBar.progress += 1
            binding.questionNumberTxt.text = "Question" + binding.progressBar.progress + "/$count"
            binding.questionTxt.text=receivedList[position].question


            loadAnswers()
        },1000)
    }
    private fun loadAnswers() {
        val users: MutableList<String> = mutableListOf()
        users.add(receivedList[position].answer_1.toString())
        users.add(receivedList[position].answer_2.toString())
        users.add(receivedList[position].answer_3.toString())
        users.add(receivedList[position].answer_4.toString())

        if (receivedList[position].clickedAnswer != null) users.add(receivedList[position].clickedAnswer.toString())

        val questionAdapter by lazy {
            QuestionAdapter(
                receivedList[position].correctAnswer.toString(), users,receivedList[position].score, this
            )
        }

        questionAdapter.differ.submitList(users)
        binding.questionList.apply {
            layoutManager = GridLayoutManager(this@QuizActivity,2)
            adapter = questionAdapter
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,QuizLevelActivity::class.java))
        finish()
    }

}