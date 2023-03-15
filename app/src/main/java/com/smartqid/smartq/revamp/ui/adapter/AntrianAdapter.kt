package com.smartqid.smartq.revamp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.ItemAntrianBinding
import com.smartqid.smartq.revamp.domain.model.Transaction

class AntrianAdapter (val items: List<Transaction>) : RecyclerView.Adapter<AntrianAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
                ItemAntrianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(items[position])
        }
    }


    inner class ViewHolder(val view: ItemAntrianBinding):RecyclerView.ViewHolder(view.root) {
        fun bind (item: Transaction) = with(view){
        }

    }

}