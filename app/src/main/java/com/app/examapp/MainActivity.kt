package com.app.examapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examapp.adapter.ExamListAdapter
import com.app.examapp.databinding.ActivityMainBinding
import com.app.examapp.model.ExamModel
import com.app.examapp.model.QuestionModel

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
        val listQuestinModel = mutableListOf<QuestionModel>()

        // dumy question data
        listQuestinModel.add(QuestionModel("What is android ?", mutableListOf("Language","OS","Product","None"),"OS"))
        listQuestinModel.add(QuestionModel("Who owns Android ?", mutableListOf("Google","Apple","Samsung","Microsoft"),"Google"))
        listQuestinModel.add(QuestionModel("Which assistant android uses ?", mutableListOf("Siri","Cortana","Google assistant","Alexa"),"Google assistan"))
        listQuestinModel.add(QuestionModel("What programming language is primarily used for Android app development?", mutableListOf("Java","C++","Python","Kotlin"),"Java"))
        listQuestinModel.add(QuestionModel("What is the main IDE (Integrated Development Environment) used for Android app development?", mutableListOf("Eclipse","NetBeans","IntelliJ IDEA","Android Studio"),"Android Studio"))
        listQuestinModel.add(QuestionModel("What is the name of the layout file used to define the user interface of an Android app?", mutableListOf("layout.xml","design.xml","view.xml","activity_main.xml"),"activity_main.xml"))
        listQuestinModel.add(QuestionModel("What component of Android is responsible for managing app lifecycle and handling UI interactions?", mutableListOf("Activity","Service","Fragment","Intent"),"Activity"))
        listQuestinModel.add(QuestionModel("Which file in an Android project contains the metadata about the app, such as its name, version, and permissions?", mutableListOf("build.gradle","AndroidManifest.xml","settings.gradle","app.properties"),"AndroidManifest.xml"))

        val listQuestinModel2 = mutableListOf<QuestionModel>()

        listQuestinModel2.add(QuestionModel("What is the name of the database system commonly used in Android app development?", mutableListOf("MySQL","SQLite","Oracle","MongoDB"),"SQLite"))
        listQuestinModel2.add(QuestionModel("What is the minimum Android API level required to support material design components?", mutableListOf("API 14 (Android 4.0)","API 21 (Android 5.0)","API 23 (Android 6.0)","API 28 (Android 9.0)"),"API 21 (Android 5.0)"))
        listQuestinModel2.add(QuestionModel("Which method is used to fetch data from a remote server in Android app development?", mutableListOf("fetchData()","getData()","retrieveData()","AsyncTask"),"AsyncTask"))
        listQuestinModel2.add(QuestionModel("What is the file extension used for Android app packages?", mutableListOf(".apk",".app",".exe",".jar"),".apk"))
        listQuestinModel2.add(QuestionModel("Which XML-based language is used for designing user interface layouts in Android?", mutableListOf("HTML","XML","XAML","JSON"),"XML"))

        // Create a list to hold the questions
        val geographicQuestions = mutableListOf<QuestionModel>()

// Add questions to the list
        geographicQuestions.add(
            QuestionModel(
                "What is the capital of France?",
                mutableListOf("Berlin", "Rome", "Paris", "Madrid"),
                "Paris"
            )
        )

        geographicQuestions.add(
            QuestionModel(
                "Which country is known as the 'Land of the Rising Sun'?",
                mutableListOf("China", "India", "Japan", "South Korea"),
                "Japan"
            )
        )

        geographicQuestions.add(
            QuestionModel(
                "What is the largest desert in the world?",
                mutableListOf("Sahara Desert", "Gobi Desert", "Arabian Desert", "Antarctic Desert"),
                "Sahara Desert"
            )
        )

        geographicQuestions.add(
            QuestionModel(
                "Which river is the longest in the world?",
                mutableListOf("Amazon", "Nile", "Mississippi", "Yangtze"),
                "Nile"
            )
        )

        geographicQuestions.add(
            QuestionModel(
                "Where is the Great Barrier Reef located?",
                mutableListOf("Caribbean Sea", "Red Sea", "Indian Ocean", "Coral Sea"),
                "Coral Sea"
            )
        )

// Add more questions here...


        // dumy exam data
        examModelList.add(ExamModel("1","Programming","All the basic programming","1",listQuestinModel))
        examModelList.add(ExamModel("2","Computer","All the Computer Questions","20",listQuestinModel2))
        examModelList.add(ExamModel("3","Geography","Boost your geographic knowledge","15",geographicQuestions))
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = ExamListAdapter(examModelList)
        binding.recyclerView.layoutManager= LinearLayoutManager(this)  // layoutmanager
        binding.recyclerView.adapter =adapter


    }


}















