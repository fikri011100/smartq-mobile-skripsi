package com.smartqid.smartq.revamp.ui.home.history

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.orhanobut.logger.Logger
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.ItemAntrianBinding
import com.smartqid.smartq.revamp.domain.model.Transaction
import com.smartqid.smartq.revamp.ui.queue.DetailAnterianActivity
import com.smartqid.smartq.revamp.utils.StringHelper

class AntrianAdapter(
    val items: List<Transaction>,
    var context: Context,
    var ref: DatabaseReference,
    var listUID: ArrayList<String> = arrayListOf()
) :
    RecyclerView.Adapter<AntrianAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAntrianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(items[position])
        }
        ref = FirebaseDatabase.getInstance().getReference("toko")
        if (items[position].Status.equals("Menunggu Konfirmasi Batal"))
            holder.view.textStatus.setTextColor(context.getColor(R.color.danger))
        ref.child(items.get(position).uid.toString()).get().addOnSuccessListener {
//            holder.view.textAlamat.text = it.child("alamat").value.toString()
//            Glide.with(context).load(it.child("gambar").value.toString())
//                .into(holder.view.imageRiwayat)
            ref = FirebaseDatabase.getInstance().getReference("anterian")
            var queue = "0"
            ref.child("idAnterian").orderByChild(it.child("idAnterian").toString()).get().addOnSuccessListener {
                if ( it.child("urutan").value != null) {
                    queue = it.child("urutan").value.toString()
                }
                Logger.d(it.child("urutan").value.toString())
            }
            holder.view.textQueue.text = queue
        }
        var urutan = "Menunggu"
        if (!items.get(position).urutan.toString().equals("0")) {
            urutan = items.get(position).urutan.toString()
        }
        holder.view.textNoAntrian.text = urutan

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val view: ItemAntrianBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Transaction) = with(view) {
            view.textNamaToko.text = item.NamaToko
//            view.textAktif.text = StringHelper.formatDate(item.waktuDibuat)
            view.textStatus.text = item.Status
            view.textEstimasi.text = "${item.waktuDipanggil} Menit"
            view.textDate.text = StringHelper.formatDateFull(item.waktuDibuat)
            view.textHour.text = StringHelper.formatDateHour(item.waktuDibuat)

            view.cardRiwayat.setOnClickListener {
                val intent = Intent(context, DetailAnterianActivity::class.java)
                intent.putExtra("idtoko", item.uid)
                intent.putExtra("urutan", item.urutan)
                intent.putExtra("idAnterian", listUID[adapterPosition])
                context.startActivity(intent)
            }
        }
    }
}