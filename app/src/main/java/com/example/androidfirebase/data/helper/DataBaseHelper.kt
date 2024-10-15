package com.example.androidfirebase.data.helper

import android.util.Log
import com.example.androidfirebase.data.helper.AuthHelper.Companion.authHelper
import com.example.androidfirebase.data.model.TodoModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DataBaseHelper {

    companion object{
        val dbHelper = DataBaseHelper()
    }

    val dataBase = FirebaseFirestore.getInstance()

    fun insertTodo(todoModel: TodoModel)
    {
        dataBase.collection("Todo")
            .document(authHelper.user!!.uid)
            .collection("Notes")
            .add(todoModel)

        Log.d("User", "insertTodo: ======== ${authHelper.user!!.uid}")
    }

    suspend fun readTodo(): Flow<MutableList<TodoModel>> {

        return callbackFlow<MutableList<TodoModel>> {

            val snapShot = dataBase.collection("Todo")
                .document(authHelper.user!!.uid)
                .collection("Notes")
                .addSnapshotListener { value, error ->
                    if(value!=null)
                    {
                        var todoList = mutableListOf<TodoModel>()

                        for(i in value.documents)
                        {
                            val title = i["title"]
                            val desc = i["desc"]
                            val docId = i.id

                            val todoModel = TodoModel(title = title.toString(), desc = desc.toString(), docId = docId.toString())
                            todoList.add(todoModel)
                            Log.e("TAG", "readTodo:=================$todoList ")
                        }
                        trySend(todoList)
                    }
                    else
                    {
                        close(error)
                    }
                }

            awaitClose{ snapShot.remove() }
        }
    }

    fun updateTodo(todoModel: TodoModel)
    {
        dataBase.collection("Todo")
            .document(authHelper.user!!.uid)
            .collection("Notes")
            .document(todoModel.docId!!)
            .set(todoModel)
    }

    fun deleteTodo(docId:String)
    {
        dataBase.collection("Todo")
            .document(authHelper.user!!.uid)
            .collection("Notes")
            .document(docId)
            .delete()
    }
}