package com.smartqid.smartq.revamp.utils.ext

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Context.str(id: Int): String{
    return this.resources.getString(id)
}

interface FilterCallback{
    fun apply(selected: String)
    fun reset()
}