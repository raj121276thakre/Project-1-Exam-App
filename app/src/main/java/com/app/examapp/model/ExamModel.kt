package com.app.examapp.model

//recycler data
data class ExamModel(
    val id : String,
    val title : String,
    val subtitle : String,
    val time : String,
    val questionList: List<QuestionModel>
) {
    constructor(): this("","","","", emptyList())

}

// quiz page data

data class QuestionModel(
    val question : String,
    val options : List<String>,
    val correct : String
){
    constructor():this("", emptyList(),"")
}

