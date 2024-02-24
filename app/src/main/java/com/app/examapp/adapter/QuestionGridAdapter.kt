package com.app.examapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.app.examapp.R

class QuestionGridAdapter(private val context: Context, private val questionNumbers: List<String>, private val bookmarkedQuestions: List<Int>) : BaseAdapter() {

    override fun getCount(): Int {
        return questionNumbers.size
    }

    override fun getItem(position: Int): Any {
        return questionNumbers[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val view: View
//        val holder: ViewHolder
//
//        if (convertView == null) {
//            view = LayoutInflater.from(context).inflate(R.layout.question_grid_item, parent, false)
//            holder = ViewHolder()
//            holder.questionNumber = view.findViewById(R.id.question_number)
//            view.tag = holder
//        } else {
//            view = convertView
//            holder = view.tag as ViewHolder
//        }
//
//        // Set the question number
//        holder.questionNumber.text = questionNumbers[position]
//
//        return view
        var view = convertView
        val holder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.question_grid_item, parent, false)
            holder = ViewHolder()
            holder.questionNumber = view.findViewById(R.id.question_number)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val questionNumberTextView = holder.questionNumber
        questionNumberTextView.text = questionNumbers[position]

        // Change the background color of the grid item based on whether the question is bookmarked
        if (position in bookmarkedQuestions) {
            questionNumberTextView.setBackgroundColor(Color.parseColor("#F802EC"))
        } else {
            questionNumberTextView.setBackgroundColor(Color.GRAY)
        }

        return view!!
    }

    private class ViewHolder {
        lateinit var questionNumber: TextView
    }
}
