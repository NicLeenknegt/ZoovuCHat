package com.zoovu.zuuvochat.fragments.conversation_list

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.zoovu.zuuvochat.R
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.viewmodels.ConversationViewModel
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk27.coroutines.onClick

class ConversationListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val conViewModel: ConversationViewModel,
    private val context: Context
):BaseAdapter() {
    var dataset = ArrayList<Model.Conversation>()
    private class ConversationViewHolder {
        internal var image:ImageView? = null
        internal var text:TextView? = null
    }

    init {
        if (conViewModel.conversations.value != null) {
            dataset = conViewModel.conversations.value!!
            this.notifyDataSetChanged()
        }

        conViewModel.conversations.observe(lifecycleOwner, Observer {
            if (it != null) {
                dataset = it
                this.notifyDataSetChanged()
            }
        })
    }

    override fun getCount(): Int {
        return dataset.size
    }

    override fun getItem(position: Int): Model.Conversation {
        return dataset[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var viewHolder:ConversationViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.conversation_grid_item, parent, false)

            viewHolder = ConversationViewHolder()
            viewHolder.image = view!!.findViewById(R.id.conversation_image_view) as ImageView
            viewHolder.text = view!!.findViewById(R.id.conversation_text_view) as TextView
        } else {
            viewHolder = view.tag as ConversationViewHolder
        }

        var conversation = getItem(position)
        viewHolder.text!!.text = conversation.name
        viewHolder.image!!.imageResource = R.drawable.ic_launcher_background

        view!!.onClick {
            conViewModel.selectedConversation.value = getItem(position)
            conViewModel.startConversation()
        }

        view.tag = viewHolder

        return view
    }
}