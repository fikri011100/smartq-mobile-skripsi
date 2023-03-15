package com.smartqid.smartq.revamp.domain.model.response

import com.smartqid.smartq.revamp.domain.model.Store

data class ResponseStore(
    var products: ArrayList<Store>? = null,
    var exception: Exception? = null
)