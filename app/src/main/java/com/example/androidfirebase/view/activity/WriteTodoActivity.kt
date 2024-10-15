package com.example.androidfirebase.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidfirebase.R
import com.example.androidfirebase.data.helper.DataBaseHelper.Companion.dbHelper
import com.example.androidfirebase.data.model.TodoModel
import com.example.androidfirebase.databinding.ActivityWriteTodoBinding

class WriteTodoActivity : AppCompatActivity() {

    private var docId: String?=null
    private lateinit var binding:ActivityWriteTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWriteTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUpdateTodo()
        backClick()
        initInsertTodo()
        initDeleteTodo()

    }

    private fun initInsertTodo()
    {
        binding.saveBtnTodo.setOnClickListener {

            val title = binding.edtTitle.text.toString()
            val desc = binding.edtDesc.text.toString()

            val todoModel = TodoModel(title = title, desc = desc,docId)

            if(docId==null) {
                dbHelper.insertTodo(todoModel)
            }
            else
            {
                dbHelper.updateTodo(todoModel)
            }

            finish()

            Log.i("Insert", "initInsertTodo: ==========$todoModel")
        }
    }

    private fun initUpdateTodo()
    {
        var title = intent.getStringExtra("title")
        var desc = intent.getStringExtra("desc")
        docId = intent.getStringExtra("docId")

        binding.edtTitle.setText(title)
        binding.edtDesc.setText(desc)

    }

    private fun backClick()
    {
        binding.backBtnTodo.setOnClickListener {
            finish()
        }
    }

    private fun initDeleteTodo()
    {
        binding.deleteTodoBtn.setOnClickListener {
            dbHelper.deleteTodo(docId!!)
            finish()
        }
    }

}