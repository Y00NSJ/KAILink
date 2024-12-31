package com.example.kailink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kailink.R
import com.example.kailink.model.Message

class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val botMessageView: View = view.findViewById(R.id.left_chat)
        val botMessageTextView: TextView = view.findViewById(R.id.left_chat_text)
        val userMessageView: View = view.findViewById(R.id.right_chat)
        val userMessageTextView: TextView = view.findViewById(R.id.right_chat_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        // 처음엔 말풍선 안 보이도록 설정
        holder.userMessageView.visibility = View.GONE
        holder.botMessageView.visibility = View.GONE

        // 발신자에 따라 user 또는 bot 말풍선 보이도록 설정
        if (message.sentBy == Message.SENT_BY_ME) {
            holder.userMessageTextView.text = message.message
            holder.userMessageView.visibility = View.VISIBLE
        } else if (message.sentBy == Message.SENT_BY_BOT) {
            holder.botMessageTextView.text = message.message
            holder.botMessageView.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = messages.size
}
