package com.app.examapp.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.examapp.MainActivity
import com.app.examapp.databinding.ActivitySignInBinding


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*after clicking on the text "Not Registered Yet , Sign Up !"
         the signup page will display */
        binding.textGotoSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java )
            startActivity(intent)
            finish() // Finish SignInActivity to remove it from back stack
        }

        //after clicking on the "SignIn" button the below code will execute
        binding.btnSignIn.setOnClickListener {
            Toast.makeText(this,"SignIn button will be implemented soon",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)
            finish()
        }

    }
}