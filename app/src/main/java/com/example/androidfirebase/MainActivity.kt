package com.example.androidfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidfirebase.data.helper.DataBaseHelper.Companion.dbHelper
import com.example.androidfirebase.data.model.TodoModel
import com.example.androidfirebase.databinding.ActivityMainBinding
import com.example.androidfirebase.view.activity.WriteTodoActivity
import com.example.androidfirebase.view.adapter.TodoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    var todoList = mutableListOf<TodoModel>()
    private lateinit var todoAdapter:TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initTodoIntent()

        todoAdapter = TodoAdapter(todoList)
        Log.e("TAG", "onCreate: =============== $todoList", )
        binding.todoRecyclerView.adapter = todoAdapter

        GlobalScope.launch {
            withContext(Dispatchers.Main)
            {
                dbHelper.readTodo().collect{
                    todoAdapter.dataChange(it)
                    Log.d("TAG", "onCreate ============ $it ")
                }
            }
        }


    }

    private fun initTodoIntent()
    {
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this,WriteTodoActivity::class.java))
        }
    }
}