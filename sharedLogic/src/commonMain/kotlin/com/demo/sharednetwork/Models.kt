package com.demo.sharednetwork

data class KullaniciOzeti(
    val adSoyad: String,
    val email: String,
    val sehir: String,
    val gonderiSayisi: Int,
    val sonGonderiBasliklari: List<String>,
)

data class DemoSonuc(val veri: KullaniciOzeti?, val hata: String?)
