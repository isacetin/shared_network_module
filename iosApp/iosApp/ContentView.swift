import SwiftUI
import SharedNetwork

struct ContentView: View {
    @State private var yukleniyor = false
    @State private var metin = ""

    var body: some View {
        VStack(spacing: 16) {
            Button(yukleniyor ? "Yükleniyor..." : "Getir") {
                yukleniyor = true
                Task {
                    do {
                        let sonuc = try await SharedNetwork(baseUrl: "https://jsonplaceholder.typicode.com").kullaniciOzetiGetir(userId: 1)
                        if let veri = sonuc.veri {
                            let basliklar = veri.sonGonderiBasliklari.map { "• \($0)" }.joined(separator: "\n")
                            metin = "\(veri.adSoyad)\n\(veri.email)\n\(veri.sehir)\nGönderi sayısı: \(veri.gonderiSayisi)\n\n\(basliklar)"
                        } else {
                            metin = "Hata: \(sonuc.hata ?? "bilinmiyor")"
                        }
                    } catch {
                        // Sadece iptal (CancellationException) buraya düşer.
                        metin = "İptal edildi"
                    }
                    yukleniyor = false
                }
            }
            .disabled(yukleniyor)

            Text(metin)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
