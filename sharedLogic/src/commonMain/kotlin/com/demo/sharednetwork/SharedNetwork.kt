package com.demo.sharednetwork

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json

/**
 * @param baseUrl İsteklerin atılacağı sunucu, örn. "https://jsonplaceholder.typicode.com".
 * Sondaki "/" olsa da olmasa da çalışır.
 */
class SharedNetwork(baseUrl: String) {

    private val baseUrl = baseUrl.trimEnd('/')

    // Engine (Android: OkHttp, iOS: Darwin) bağımlılıklardan otomatik seçilir.
    private val client = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    /** iOS'a asla exception fırlatmaz; hata durumunda DemoSonuc.hata dolu gelir. */
    suspend fun kullaniciOzetiGetir(userId: Int): DemoSonuc =
        try {
            val user: UserDto = client.get("$baseUrl/users/$userId").body()
            val posts: List<PostDto> = client.get("$baseUrl/posts") {
                parameter("userId", user.id)
            }.body()
            DemoSonuc(veri = user.toKullaniciOzeti(posts), hata = null)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            DemoSonuc(veri = null, hata = e.message ?: e.toString())
        }
}
