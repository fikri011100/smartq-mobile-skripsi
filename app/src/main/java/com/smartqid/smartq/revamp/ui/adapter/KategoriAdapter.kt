package com.smartqid.smartq.revamp.ui.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.smartqid.smartq.databinding.ItemKategoriBinding
import com.smartqid.smartq.revamp.domain.model.Category
import com.smartqid.smartq.ui.DetailActivity


class KategoriAdapter(val items: ArrayList<Category>, var ref: DatabaseReference, val context: Context) :
    RecyclerView.Adapter<KategoriAdapter.ViewHolder>() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemKategoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(items[position])
        }
    }


    inner class ViewHolder(val view: ItemKategoriBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Category) = with(view) {
            Glide.with(context).load(item.image).into(view.listKategori)
            view.textKategori.text = item.nama
            val query: Query = ref.orderByChild("kategori").equalTo(item.nama)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        view.listKategori.setOnClickListener {
                            sharedPreferences =
                                context.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                            val intent = Intent(context, DetailActivity::class.java)
                            val Editor: SharedPreferences.Editor = sharedPreferences.edit()
                            Editor.putString("Kategori", item.nama)
                            Editor.apply()
                            context.startActivity(intent)
                        }
                    } else {
                        view.listKategori.setOnClickListener {
                            Toast.makeText(context, item.nama, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

}