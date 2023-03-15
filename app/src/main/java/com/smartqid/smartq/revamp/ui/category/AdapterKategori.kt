package com.smartqid.smartq.revamp.ui.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.ItemTokoBinding
import com.smartqid.smartq.revamp.domain.model.Store

class AdapterKategori (private val c: Context, val items: ArrayList<Store>) : RecyclerView.Adapter<AdapterKategori.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTokoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(val view: ItemTokoBinding):RecyclerView.ViewHolder(view.root) {
        fun bind (item: Store) = with(view){
            Glide.with(c).load(item.gambar).into(view.fotoToko)
            namaToko.text = item.namaToko
            namaLocation.text = item.alamat
            cardToko.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("uid", item.uid)
                bundle.putString("nama", item.namaToko)
                bundle.putString("kategori", item.kategori)
                bundle.putString("gambar", item.gambar)
                bundle.putString("alamat", item.alamat)
                bundle.putString("kota", item.kota)
                bundle.putString("waktuBuka", item.waktuBuka)
                bundle.putString("hariAktif", item.hariAktif)
                bundle.putString("desc", item.desc)
                bundle.putString("phone", item.phone)
                bundle.putString("idAnterian", item.idAnterian)
                bundle.putString("estimasi", item.estimasi)
                bundle.putString("instagram", item.instagram)
                bundle.putString("facebook", item.facebook)
                view.root.findNavController().navigate(R.id.action_detailKategori_to_tokoFragment, bundle)
            }
        }
    }
}