package com.app.examapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examapp.adapter.ExamListAdapter
import com.app.examapp.authentication.SignInActivity
import com.app.examapp.databinding.ActivityMainBinding
import com.app.examapp.model.ExamModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding    // declaration of binding
    lateinit var examModelList: MutableList<ExamModel>
    lateinit var adapter: ExamListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)   // initialization of binding
        setContentView(binding.root)

        examModelList = mutableListOf()
        getDataFromFirebase()

        // Setup logout button click listener
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }


    }

    // getting data from firebase
    private fun getDataFromFirebase() {
        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val quizModel = snapshot.getValue(ExamModel::class.java)
                        if (quizModel != null) {
                            examModelList.add(quizModel)
                        }
                    }

                }

                setupRecyclerView()

            }


    }

    //setting data to recyclerview
    private fun setupRecyclerView() {
        binding.progressBar.visibility = View.GONE
        adapter = ExamListAdapter(examModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)  // layoutmanager
        binding.recyclerView.adapter = adapter


    }

    @SuppressLint("MissingInflatedId")
    // logout dialog
    private fun showLogoutDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.logout_dialog, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.show()

        val btnYes = dialogView.findViewById<Button>(R.id.btnYes)
        val btnNo = dialogView.findViewById<Button>(R.id.btnNo)

        btnYes.setOnClickListener {
            // Perform logout action
            alertDialog.dismiss()
            // Call your logout function here
            logout()
        }

        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    // Function to logout user
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        navigateToSignIn()
    }

    // Function to navigate to SignInActivity
    private fun navigateToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


}















