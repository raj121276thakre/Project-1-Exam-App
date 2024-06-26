package com.app.examapp.model

//main activity recycler data
data class ExamModel(
    val id : String,
    val title : String,
    val subtitle : String,
    val time : String,
    val questionList: List<QuestionModel>
) {
    constructor(): this("","","","", emptyList())

}

// quiz exam page data

data class QuestionModel(
    val index: Int,
    val question : String,
    val options : List<String>,
    val correct : String
){
    constructor():this(0,"", emptyList(),"")
}

