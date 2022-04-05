package com.example.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guessthenumber.DB.DBHelper
import com.example.guessthenumber.data.message.message
import com.example.guessthenumber.data.message.messageAdapter

private lateinit var messageAdapter: messageAdapter

class Ranking : AppCompatActivity() {
    internal lateinit var db: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        db = DBHelper(this)

        var new = message("1","VI","3")
        db.addMessage(new)

        val listMessage = db.allMessage
        listMessage.reversed()
        messageAdapter = messageAdapter(listMessage)

        val messageList = findViewById<RecyclerView>(R.id.recyclerView)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)



    }
}

/*
for (a in 1..10) {
            val new = messageAdapter()
            messageAdapter.addMessage(new)

        }
*/