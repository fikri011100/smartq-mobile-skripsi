package com.smartqid.smartq.revamp.domain.model.response

import com.smartqid.smartq.revamp.domain.model.Transaction

data class ResponseTransaction(
    var products: ArrayList<Transaction>? = null,
    var listUid: ArrayList<String>? = null,
    var exception: Exception? = null
)