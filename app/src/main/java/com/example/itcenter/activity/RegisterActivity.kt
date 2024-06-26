package com.example.itcenter.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.itcenter.R
import com.example.itcenter.Utility
import com.example.itcenter.databinding.ActivityRegisterBinding
import com.example.itcenter.model.AllStudentModel
import com.example.itcenter.utils.PrefUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener {
            createAccount()
            val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("group", "x")
            editor.apply()
        }
        binding.logIn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
    fun createAccount() {
        val email: String = binding.etEmail.getText().toString()
        val password: String = binding.etPassword.getText().toString()
        val name: String = binding.etName.getText().toString()
        val confirmPassword: String = binding.etRePassword.getText().toString()
        val isValidate: Boolean = validateDate(email, password, confirmPassword)
        if (!isValidate) {
            return
        }
        createAccountInFirebase(email, password,name)
    }
    fun createAccountInFirebase(email: String?, password: String?,name: String?) {
        changeInProgress(true)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this@RegisterActivity,
                OnCompleteListener<AuthResult?> { task ->
                    changeInProgress(false)
                    if (task.isSuccessful) {
                        Utility.showToast(
                            this@RegisterActivity,
                            "Sucsessfully create acoount, Check email to verify"
                        )
                        firebaseAuth.currentUser!!.sendEmailVerification()
                        firebaseAuth.signOut()
                        showCustomDialogBox(email, password,name)
                    } else {
                        Utility.showToast(
                            this@RegisterActivity,
                            task.exception!!.localizedMessage
                        )
                    }
                })
    }

    private fun showCustomDialogBox(email: String?, password: String?,name: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.check_verification)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val exit: ImageView = dialog.findViewById(R.id.exit)
        val text: TextView = dialog.findViewById(R.id.tvCheck)
        val txt = "We have sent a confirmation link to your $email account. Confirm the account and click the button below"
        text.text = txt
        val check: FloatingActionButton = dialog.findViewById(R.id.floatingActionButton)
        val progress: ProgressBar = dialog.findViewById(R.id.progress_bar2)
//        var inProgress2: Boolean = false
        check.setOnClickListener {
            loginAccountInFirebase(email, password,name,progress,check)
            progress.visibility = View.VISIBLE
            check.visibility = View.GONE

        }
        exit.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    fun changeInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnSignUp.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnSignUp.visibility = View.VISIBLE
        }
    }
    private fun validateDate(email: String, password: String, confirmPassword: String): Boolean {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Email is invalid "
            return false
        }
        if (password.length < 6) {
            binding.etPassword.error = "Password length is invalid"
            return false
        }
        if (password != confirmPassword) {
            binding.etRePassword.error = "Password not matched"
            return false
        }
        return true
    }
    fun loginAccountInFirebase(email: String?, password: String?,name: String?, progressBar: ProgressBar, dialogButton: FloatingActionButton) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener { task ->
            progressBar.visibility = View.GONE
            dialogButton.visibility = View.VISIBLE
            if (task.isSuccessful) {
                if (firebaseAuth.currentUser!!.isEmailVerified) {

//                    var pref = PrefUtils(this)
//                    var student = AllStudentModel(0,email,"","","x","",0,"")
//                    pref.setStudent(student)
//                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
//                    finish()
                } else {
                    Utility.showToast(
                        this@RegisterActivity,
                        "email not verified, Please verify your email."
                    )
                }
            } else {
                Utility.showToast(this@RegisterActivity, task.exception!!.localizedMessage)
            }
        }
    }

}