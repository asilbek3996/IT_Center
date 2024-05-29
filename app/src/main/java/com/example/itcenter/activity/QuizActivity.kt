package com.example.itcenter.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
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
import com.example.itcenter.model.viewmodel.MainViewModel
import com.example.itcenter.utils.Constants
import com.example.itcenter.utils.PrefUtils

class QuizActivity : AppCompatActivity(), score {
    private lateinit var binding: ActivityQuizBinding
    private var position: Int = 0
    private var receivedList: MutableList<QuestionModel> = mutableListOf()
    private var correctAnswersCount = 0
    private var wrongAnswersCount = 0
    private var score = 0
    private var level = 0
    private var level2 = 0
    private var javob = "0"
    private lateinit var timer: CountDownTimer
    lateinit var viewModel: MainViewModel

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        level = intent.getIntExtra("level",1)
        binding.framelayout.visibility = android.view.View.VISIBLE
        binding.framelayout.startAnimation(fadeInAnimation)
        binding.questionList.visibility = android.view.View.VISIBLE
        binding.questionList.startAnimation(fadeInAnimation)

        val window: Window = this@QuizActivity.window
        window.statusBarColor = ContextCompat.getColor(this@QuizActivity, R.color.grey)
        viewModel.questionData.observe(this){
            for (i in it){
                if (i.level == level){
                   receivedList.add(i)
                }
            }
            Toast.makeText(this, "${it.size}", Toast.LENGTH_SHORT).show()
            val count = receivedList.size
            val pref = PrefUtils(this)
            val name = pref.getStudent(Constants.fullName)
            val txt = "Salom $name"
            binding.tvMain.text = txt

            binding.back.setOnClickListener {
                startActivity(Intent(this, QuizLevelActivity::class.java))
                finish()
            }

            binding.apply {
                questionNumberTxt.text = "Savollar 1/$count"
                progressBar.progress = 1

            questionTxt.text = receivedList[position].question

                loadAnswers()
                progressBar.max = count

                rightArrow.setOnClickListener {
                    if (progressBar.progress == count) {
                        navigateToScoreActivity(level)
                        return@setOnClickListener
                    }
                    moveToNextQuestion()
                }
            }

            // Start the timer for the first question
            resetAndStartTimer()
        }
        viewModel.getQuestions()
    }

    private fun moveToNextQuestion() {
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        if (position < receivedList.size - 1) {
            position++
            binding.progressBar.progress = position + 1
            binding.questionNumberTxt.text = "Savollar ${binding.progressBar.progress}/${receivedList.size}"
            binding.questionTxt.text = receivedList[position].question
            binding.framelayout.startAnimation(fadeInAnimation) // Animatsiya framelayout uchun
            binding.questionList.startAnimation(fadeInAnimation) // Animatsiya questionList uchun
            loadAnswers()
            resetAndStartTimer()  // Reset and start the timer for the new question
        } else {
            navigateToScoreActivity(level)
        }
    }

    private fun loadAnswers() {
        val users: MutableList<String> = mutableListOf()
        users.add(receivedList[position].a.toString())
        users.add(receivedList[position].b.toString())
        users.add(receivedList[position].c.toString())
        users.add(receivedList[position].d.toString())

        if (receivedList[position].clickedAnswer != null) {
            receivedList[position].clickedAnswer = ""
//            users.add(receivedList[position].clickedAnswer.toString())
        }
        var score = 100/receivedList.size
        val questionAdapter = QuestionAdapter(
            receivedList[position].right.toString(), users, score, this
        )

        questionAdapter.differ.submitList(users)
        binding.questionList.apply {
            layoutManager = GridLayoutManager(this@QuizActivity, 2)
            adapter = questionAdapter
        }
    }

    private fun navigateToScoreActivity(level: Int) {
        timer.cancel()
        val intent = Intent(this@QuizActivity, ScoreActivity::class.java).apply {
            putExtra("right", correctAnswersCount)
            putExtra("wrong", wrongAnswersCount)
            putExtra("Score", score)
            putExtra("level", level)
        }
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        timer.cancel()  // Cancel the timer when back button is pressed
        startActivity(Intent(this, QuizLevelActivity::class.java))
        finish()
    }

    private fun resetAndStartTimer() {
        if (::timer.isInitialized) {
            timer.cancel()  // Cancel the previous timer if it exists
        }
        timer = object : CountDownTimer(11000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timer.text = (millisUntilFinished / 1000).toString()
            }
            override fun onFinish() {
                if (javob == "0"){
                    timeOut()
                }else {
                    moveToNextQuestion()  // Move to the next question when the timer finishes
                    javob = "0"
                }
            }
        }
        timer.start()  // Start the new timer
    }
    fun timeOut(){
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setMessage("Sizning vaqtingiz tugadi testlarni qaytadan yeching yoki orqga qayting")
            alertDialogBuilder.setPositiveButton("Qayta boshlash", DialogInterface.OnClickListener { dialog, which ->
                clear()
                timer.start()
                dialog.dismiss()
            })
            alertDialogBuilder.setNegativeButton("Ortga qaytish", DialogInterface.OnClickListener { dialog, which ->
                timer.cancel()  // Cancel the timer when back button is pressed
                startActivity(Intent(this, QuizLevelActivity::class.java))
                finish()
                dialog.dismiss()
            })
            alertDialogBuilder.setCancelable(false)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
    }
    fun clear(){
        binding.progressBar.progress = 0
        correctAnswersCount = 0
        wrongAnswersCount = 0
        position = -1
        moveToNextQuestion()
        loadAnswers()
    }
    override fun amount(number: Int, clickedAnswer: String, rightAnswer: Int, wrongAnswer: Int) {
            correctAnswersCount +=rightAnswer
            wrongAnswersCount += wrongAnswer
            score += number
            receivedList[3].clickedAnswer = clickedAnswer
        if (rightAnswer == 1){
            javob = "ok"
        }else{
            javob = "no"
        }
    }
}