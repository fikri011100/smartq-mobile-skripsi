package com.smartqid.smartq.revamp.ui.home.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.ItemNotificationBinding
import com.smartqid.smartq.revamp.domain.model.Notification

class AdapterNotification(private val c: Context, val items: ArrayList<Notification>) :
    RecyclerView.Adapter<AdapterNotification.ViewHolder>() {

    inner class ViewHolder(val view: ItemNotificationBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Notification) = with(view) {
            textTitle.text = item.title
            textDesc.text = item.desc
            textTime.text = item.time
            if (item.status) {
                imgNotif.setImageResource(R.drawable.ic_volume_up)
            } else {
                imgNotif.setImageResource(R.drawable.ic_volume_off)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}