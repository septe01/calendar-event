package com.example.calendarevent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_event.view.*

class EventAdapter(private val context: Context) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    var data = ArrayList<EventData>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventData: EventData = data[position]

        holder.itemView.vLine.setBackgroundColor(ContextCompat.getColor(context, eventData.color))

        when (eventData.type) {
            Utils.shift -> {
                holder.itemView.tvShift.text = ("${eventData.startTime} - ${eventData.endTime}")
                holder.itemView.tvTime.visibility = View.VISIBLE
                holder.itemView.tvTime.text = eventData.name
            }

            else -> {
                holder.itemView.tvTime.visibility = View.GONE
                holder.itemView.tvShift.text = eventData.name
            }
        }
    }
}