package com.app.examsoftware

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.app.examapp.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding  // binding declaration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater) // binding declaration
        setContentView(binding.root)

        /*after clicking on the text "Already Registered , Sign In !"
         the signin page will display */
        binding.textGotoSignin.setOnClickListener{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish() // Finish SignUpActivity to remove it from back stack
        }

        //after clicking on the "Sign up" button the below code will execute
        binding.btnSignUp.setOnClickListener {
            Toast.makeText(this,"SignUp button will be implemented soon", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,SignInActivity::class.java )
            startActivity(intent)
            finish()
        }

    }
}