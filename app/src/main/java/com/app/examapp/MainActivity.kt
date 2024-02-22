package com.app.examapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examapp.adapter.ExamListAdapter
import com.app.examapp.databinding.ActivityMainBinding
import com.app.examapp.model.ExamModel
import com.app.examapp.model.QuestionModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {

    lateinit var  binding: ActivityMainBinding    // declaration of binding
    lateinit var examModelList: MutableList<ExamModel>
    lateinit var adapter: ExamListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)   // initialization of binding
        setContentView(binding.root)

        examModelList = mutableListOf()
        getDataFromFirebase()



    }

    private fun getDataFromFirebase(){

        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot->
                if (dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children){
                        val quizModel = snapshot.getValue(ExamModel::class.java)
                        if (quizModel != null) {
                            examModelList.add(quizModel)
                        }
                    }

                }

                setupRecyclerView()

            }





    }

    private fun setupRecyclerView() {
        adapter = ExamListAdapter(examModelList)
        binding.recyclerView.layoutManager= LinearLayoutManager(this)  // layoutmanager
        binding.recyclerView.adapter =adapter


    }


}















