package com.example.androidfirebase.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidfirebase.R
import com.example.androidfirebase.data.model.TodoModel
import com.example.androidfirebase.databinding.TodoSampleLayoutBinding
import com.example.androidfirebase.view.activity.WriteTodoActivity

class TodoAdapter(var todoList:MutableList<TodoModel>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : ViewHolder(itemView)
    {
        val binding = TodoSampleLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_sample_layout,parent,false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.setTitle.text = todoList[position].title
        holder.binding.setDesc.text = todoList[position].desc
        holder.binding.cardBackground.setOnClickListener {
            val intent = Intent(holder.itemView.context,WriteTodoActivity::class.java)
            intent.putExtra("title",todoList[position].title)
            intent.putExtra("desc",todoList[position].desc)
            intent.putExtra("docId",todoList[position].docId)
            holder.itemView.context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun dataChange(list:MutableList<TodoModel>)
    {
        todoList = list
        notifyDataSetChanged()
    }
}