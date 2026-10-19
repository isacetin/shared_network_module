package com.isacetin.shared_network_module

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.sharednetwork.DemoSonuc
import com.demo.sharednetwork.SharedNetwork
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    MaterialTheme {
        val network = remember { SharedNetwork(baseUrl = "https://jsonplaceholder.typicode.com") }
        val scope = rememberCoroutineScope()
        var yukleniyor by remember { mutableStateOf(false) }
        var sonuc by remember { mutableStateOf<DemoSonuc?>(null) }

        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                enabled = !yukleniyor,
                onClick = {
                    scope.launch {
                        yukleniyor = true
                        sonuc = network.kullaniciOzetiGetir(1)
                        yukleniyor = false
                    }
                },
            ) {
                Text(if (yukleniyor) "Yükleniyor..." else "Getir")
            }

            val veri = sonuc?.veri
            val hata = sonuc?.hata
            when {
                veri != null -> Text(
                    text = """
                        |${veri.adSoyad}
                        |${veri.email}
                        |${veri.sehir}
                        |Gönderi sayısı: ${veri.gonderiSayisi}
                        |
                        |${veri.sonGonderiBasliklari.joinToString("\n") { "• $it" }}
                    """.trimMargin(),
                    modifier = Modifier.padding(top = 16.dp),
                )
                hata != null -> Text("Hata: $hata", modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}
