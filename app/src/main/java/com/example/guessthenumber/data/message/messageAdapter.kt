package com.example.guessthenumber.data.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guessthenumber.R

class messageAdapter(
    private val messages: MutableList<message>
) : RecyclerView.Adapter<messageAdapter.messageViewHolder>() {
    class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idr = itemView.findViewById<TextView>(R.id.text_pozycja)
        val user = itemView.findViewById<TextView>(R.id.text_user_name)
        val score = itemView.findViewById<TextView>(R.id.text_user_points)

        fun bind(curMessage: message) {
            idr.text = curMessage.id_rank
            user.text = curMessage.displayName
            score.text = curMessage.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    fun addMessage(message: message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        val curMessage = messages[position]
        holder.bind(curMessage)
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}