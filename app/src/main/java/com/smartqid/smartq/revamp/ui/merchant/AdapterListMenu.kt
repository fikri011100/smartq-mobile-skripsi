package com.smartqid.smartq.revamp.ui.merchant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartqid.smartq.revamp.utils.StringHelper
import com.smartqid.smartq.databinding.ItemListMenuBinding
import com.smartqid.smartq.revamp.domain.model.Menu

class AdapterListMenu (private val c: Context, val items: ArrayList<Menu>) : RecyclerView.Adapter<AdapterListMenu.ViewHolder>()  {

    inner class ViewHolder(val view: ItemListMenuBinding):RecyclerView.ViewHolder(view.root) {
        fun bind (item: Menu) = with(view){
            textTitle.text = item.menu
            textDesc.text = StringHelper.convertFormatPrice(item.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListMenu.ViewHolder {
        val binding = ItemListMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterListMenu.ViewHolder, position: Int) {
        with(holder) {
            bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}