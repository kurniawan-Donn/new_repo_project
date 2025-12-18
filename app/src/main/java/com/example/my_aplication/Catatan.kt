package com.example.my_aplication

data class Catatan(
    val id: Int,
    val judul: String,
    val deskripsi: String = "",
    val tanggal: String? = null,
    val waktu: String? = null,
)