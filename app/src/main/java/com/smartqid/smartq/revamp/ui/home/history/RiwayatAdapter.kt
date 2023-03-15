package com.smartqid.smartq.revamp.ui.home.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.ItemRiwayatBinding
import com.smartqid.smartq.revamp.domain.model.Transaction
import com.smartqid.smartq.revamp.utils.StringHelper


class RiwayatAdapter(var items: List<Transaction>, var context: Context, var ref: DatabaseReference) :
    RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(items[position])
        }
        ref = FirebaseDatabase.getInstance().getReference("toko")
        if (items[position].Status.equals("Dibatalkan"))
            holder.view.textStatus.setTextColor(context.getColor(R.color.danger))
        ref.child(items.get(position).uid.toString()).get().addOnSuccessListener {
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val view: ItemRiwayatBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Transaction) = with(view) {
            view.textNamaToko.text = item.NamaToko
//            view.textAktif.text = StringHelper.formatDate(item.waktuDibuat)
            view.textStatus.text = item.Status
        }
    }
}
