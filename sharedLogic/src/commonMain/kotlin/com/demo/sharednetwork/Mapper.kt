package com.demo.sharednetwork

internal fun UserDto.toKullaniciOzeti(posts: List<PostDto>): KullaniciOzeti =
    KullaniciOzeti(
        adSoyad = name,
        email = email,
        sehir = address.city,
        gonderiSayisi = posts.size,
        // Backend başlıkları küçük harfle döndürüyor; düzeltmeyi bir kere burada yapıyoruz,
        // Android ve iOS ayrı ayrı uğraşmıyor.
        sonGonderiBasliklari = posts.take(3).map { post ->
            post.title.replaceFirstChar { it.uppercase() }
        },
    )
