package com.app.examapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.examapp.QuizExamActivity
import com.app.examapp.databinding.ExamItemBinding
import com.app.examapp.model.ExamModel

class ExamListAdapter(private val examModelList: List<ExamModel>):
    RecyclerView.Adapter<ExamListAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ExamItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(model: ExamModel){
            //bind all the views
            binding.apply {
                examTitleText.text = model.title
                examSubtitleText.text = model.subtitle
                examTimeText.text = model.time + " min"

                // onclick for each item in recyclerview
                root.setOnClickListener{
                    //after clicking on item the QuizExamActivity will be open
                    val intent = Intent(root.context,QuizExamActivity::class.java)
                    QuizExamActivity.questionModelList = model.questionList   //questionlist
                    QuizExamActivity.time = model.time  //time
                    root.context.startActivity(intent)

                }


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val binding = ExamItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return examModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(examModelList[position])
    }

}