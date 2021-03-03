package com.example.chatlikescrollingmedium


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter() :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val dataSet: ArrayList<String> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageTV: TextView = view.findViewById(R.id.messageTV)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_item, viewGroup, false)

        return ViewHolder(view)
    }

    fun setData(newData: ArrayList<String>) {
        dataSet.addAll(0, newData)
        this.notifyItemRangeInserted(0, newData.size)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.messageTV.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size
}

