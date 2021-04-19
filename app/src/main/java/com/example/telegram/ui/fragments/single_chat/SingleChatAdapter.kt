package com.example.telegram.ui.fragments.single_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import asTime
import com.example.telegram.R
import com.example.telegram.database.CURRENT_UID
import com.example.telegram.models.CommonModel
import kotlinx.android.synthetic.main.message_item.view.*

class SingleChatAdapter: RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var mlistMessagesCache = mutableListOf<CommonModel>()

    class SingleChatHolder(view: View): RecyclerView.ViewHolder(view){
        val blockUserMessage:ConstraintLayout = view.bloc_user_message
        val chatUserMessage:TextView = view.chat_user_message
        val chatUserMessageTime:TextView = view.chat_user_message_time

        val blockReceivedMessage:ConstraintLayout = view.bloc_received_message
        val chatReceivedMessage:TextView = view.chat_received_message
        val chatReceivedMessageTime:TextView = view.chat_received_message_time

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent,false)
        return SingleChatHolder(view)
    }

    override fun getItemCount(): Int = mlistMessagesCache.size

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (mlistMessagesCache[position].from == CURRENT_UID){
            holder.blockUserMessage.visibility = View.VISIBLE
            holder.blockReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = mlistMessagesCache[position].text
            holder.chatUserMessageTime.text =
                mlistMessagesCache[position].timeStamp.toString().asTime()
        } else {
            holder.blockUserMessage.visibility = View.GONE
            holder.blockReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = mlistMessagesCache[position].text
            holder.chatReceivedMessageTime.text =
                mlistMessagesCache[position].timeStamp.toString().asTime()
        }
    }


    fun addItem(
        item:CommonModel,
        toBottom:Boolean,
        onSuccess: () -> Unit
    ) {
        if (toBottom) {
            if (!mlistMessagesCache.contains(item)) {
                mlistMessagesCache.add(item)
                notifyItemInserted(mlistMessagesCache.size)
            }
        } else{
            if (!mlistMessagesCache.contains(item)) {
                mlistMessagesCache.add(item)
                mlistMessagesCache.sortBy { it.timeStamp.toString()}
                notifyItemInserted(0)
            }
        }
        onSuccess()
    }
}


