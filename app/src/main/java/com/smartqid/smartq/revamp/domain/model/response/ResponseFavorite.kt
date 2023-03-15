package com.smartqid.smartq.revamp.domain.model.response

import com.smartqid.smartq.revamp.domain.model.Favorite

data class ResponseFavorite(
    var products: List<Favorite>? = null,
    var exception: Exception? = null
)
