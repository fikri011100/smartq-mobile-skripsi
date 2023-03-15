package com.smartqid.smartq.revamp.ui.merchant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartqid.smartq.R
import com.smartqid.smartq.databinding.FragmentListMenuBinding
import com.smartqid.smartq.revamp.common.BaseFragment
import com.smartqid.smartq.revamp.domain.model.Menu


class ListMenuFragment : BaseFragment<FragmentListMenuBinding>() {

    private lateinit var lists: ArrayList<Menu>

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListMenuBinding
        get() = FragmentListMenuBinding::inflate

    override fun initObservable() {

    }

    override fun initView() {
        setData()

        val adapterKat = AdapterListMenu(requireContext(), lists)
        binding.rvListMenu.apply {
            adapter = adapterKat
            layoutManager = LinearLayoutManager(requireContext())
        }
        val categories = resources.getStringArray(R.array.arr_category)
        binding.edtPrice.setText("Jenis Kategori", false)
        binding.edtPrice.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
        )
    }

    private fun setData() {
        lists = arrayListOf(
            Menu(
                "Promo gratis ongkir",
                "lorem ipsum dolor sit amet",
                "Potong"
            ),
            Menu(
                "Promo gratis ongkir",
                "lorem ipsum dolor sit amet",
                "Potong"
            ),
            Menu(
                "Promo gratis ongkir",
                "lorem ipsum dolor sit amet",
                "Potong"
            ),
            Menu(
                "Promo gratis ongkir",
                "lorem ipsum dolor sit amet",
                "Potong"
            ),
        )
    }
}