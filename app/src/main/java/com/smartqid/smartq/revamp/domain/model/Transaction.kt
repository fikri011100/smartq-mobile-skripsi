package com.smartqid.smartq.revamp.domain.model

data class Transaction(
        var uid: String? = null,
        var email: String? = null,
        var nama: String? = null,
        var noTelp: String? = null,
        var NamaToko : String? = null,
        var urutan : String? = null,
        var waktuDipanggil : String? = null,
        var waktuDibuat : String? = null,
        var Status : String? = null
)