package com.smartqid.smartq.revamp.ui.home.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.orhanobut.logger.Logger
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.ItemTokoBinding
import com.smartqid.smartq.revamp.domain.model.Favorite
import com.smartqid.smartq.revamp.domain.model.Store

class FavoriteAdapter (private val c: Context, val items: List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

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
        fun bind (item: Favorite) = with(view){
            var refToko = FirebaseDatabase.getInstance().getReference("toko")
            val query: Query = refToko.child(item.tokoId!!)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Glide.with(c).load(snapshot.child("gambar").value.toString()).into(view.fotoToko)
                    namaToko.text = snapshot.child("namaToko").value.toString()
                    namaLocation.text = snapshot.child("alamat").value.toString()
                    cardToko.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putString("uid", item.tokoId)
                        bundle.putString("nama", snapshot.child("namaToko").value.toString())
                        bundle.putString("kategori", snapshot.child("kategori").value.toString())
                        bundle.putString("gambar", snapshot.child("gambar").value.toString())
                        bundle.putString("alamat", snapshot.child("alamat").value.toString())
                        bundle.putString("kota", snapshot.child("kota").value.toString())
                        bundle.putString("waktuBuka", snapshot.child("waktuBuka").value.toString())
                        bundle.putString("hariAktif", snapshot.child("hariAktif").value.toString())
                        bundle.putString("desc", snapshot.child("desc").value.toString())
                        bundle.putString("phone", snapshot.child("phone").value.toString())
                        bundle.putString("idAnterian", snapshot.child("idAnterian").value.toString())
                        bundle.putString("estimasi", snapshot.child("estimasi").value.toString())
                        bundle.putString("instagram", snapshot.child("instagram").value.toString())
                        bundle.putString("facebook", snapshot.child("facebook").value.toString())
                        view.root.findNavController().navigate(R.id.action_Voucher_to_merchantFragment, bundle)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }
}