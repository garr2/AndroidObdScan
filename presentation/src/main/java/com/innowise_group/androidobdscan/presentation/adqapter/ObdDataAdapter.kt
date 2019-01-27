package com.innowise_group.androidobdscan.presentation.adqapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.innowise_group.androidobdscan.R

class ObdDataAdapter : RecyclerView.Adapter<ObdDataAdapter.Holder>() {

    var onItemClickListener: OnItemClickListener? = null
    var obdDataList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObdDataAdapter.Holder {
        var view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = obdDataList.size

    override fun onBindViewHolder(holder: ObdDataAdapter.Holder, position: Int) {
        val str = obdDataList[position]
        holder.clearView()

        holder.tvText.text = str
    }

    interface OnItemClickListener {
        fun onItemClick(id: Long)
    }

    inner class Holder : RecyclerView.ViewHolder {

        var tvText: TextView

        constructor(view: View) : super(view) {
            tvText = view.findViewById(R.id.tvText1)
        }

        fun clearView() {
            tvText.text = ""
        }
    }
}