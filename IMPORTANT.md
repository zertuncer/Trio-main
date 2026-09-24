# TRIO ANDROID PORT PROJESİ - MASTER REFERANS BELGESİ (IMPORTANT.MD)

Bu belge, Trio iOS uygulamasının Android (Kotlin/Jetpack Compose) platformuna taşınma sürecindeki **tüm fazları, mimari kararları, proje dizin yapılarını, karşılaşılan sorunları ve uyulması zorunlu kuralları** tek bir çatı altında toplamak için oluşturulmuştur. Projenin hafızasıdır.

---

## 📂 1. PROJE DİZİN YAPISI VE YENİ DOSYALARIN KONUMLARI

Proje, iç içe geçmiş klasör yapılarına sahiptir. Doğru dizinde çalışmak kritik öneme sahiptir.

*   **Ana Çalışma Alanı (Kök Dizin):** `C:\Users\ZER\Desktop\Trio-main\`
*   **Orijinal iOS (Swift) Kaynak Kodları:** `C:\Users\ZER\Desktop\Trio-main\Trio-main\Trio\Sources\` *(Referans almak için kullanılır)*
*   **Hedef Android (Kotlin) Projesi Kök Dizini:** `C:\Users\ZER\Desktop\Trio-main\AndroidAPS-master\AndroidAPS-master\`

### Yeni Eklenen Kotlin (Compose) Dosyaları Nereye Kaydediliyor?
Yeni yazılan tüm arayüz (UI) ve State dosyaları **tam olarak şu yolun altında** inşa edilmektedir:
👉 `C:\Users\ZER\Desktop\Trio-main\AndroidAPS-master\AndroidAPS-master\trio-app\src\main\java\app\trio\`

**Klasör Kırılımları:**
*   `.../app/trio/ui/theme/` -> `TrioColors.kt`, `Theme.kt`, `Typography.kt` (Nunito font entegrasyonu)
*   `.../app/trio/ui/shared/` -> Ortak bileşenler (`GlassChrome.kt`, `SettingsRowView.kt`, `PickerSettingsProvider.kt` vb.)
*   `.../app/trio/ui/home/` -> Ana ekran dosyaları (`HomeRootView.kt`, `HomeViewModel.kt`, `HomeUiState.kt` vb.)
*   `.../app/trio/ui/home/chart/` -> Vico Grafik bileşenleri (`MainChartView.kt`, `TrioChartMarker.kt`)
*   `.../app/trio/ui/home/chart/elements/` -> Grafiğin alt katmanları (`CarbView.kt`, `InsulinView.kt`, `ForecastView.kt`, `GlucoseTargetsView.kt` vb. 10 adet prototip)

---

## 🚀 2. FAZLAR VE AŞAMALAR (GÜNCEL DURUM)

### Faz 0: Keşif ve Envanter (TAMAMLANDI)
*   Tüm Swift dosyaları (205 adet) tarandı, satır sayıları ve bağımlılıkları analiz edilerek taşıma öncelikleri belirlendi (`faz0_swift_view_envanteri.md`).

### Faz 1: Altyapı ve Temel Mimari (TAMAMLANDI)
*   **Renkler & Tema:** iOS `TrioColors` yapıları Material 3 `ColorScheme` ile Android'e aktarıldı.
*   **Glassmorphism:** iOS'taki bulanık cam efekti için `GlassChrome` tonalElevation ile kurgulandı.
*   **Fontlar:** `ui-text-google-fonts` üzerinden `Nunito` fontu projeye entegre edildi ve `Typography.kt` oluşturuldu.
*   **Ayarlar:** `PickerSettingsProvider.swift` içindeki 42+ ayarın min/max ve adım değerleri Kotlin'e (`PickerSettingsProvider.kt`) aktarıldı.

### Faz 2: Home (Ana Ekran) Modülü (GÜNCEL AŞAMA - BÜYÜK ORANDA TAMAMLANDI)
Home modülü karmaşıklığı nedeniyle gruplara ayrılmıştır:
*   **Grup A (State & Provider):** `HomeUiState`, `HomeViewModel` ve hesaplayıcı handler'lar oluşturuldu.
*   **Grup B (Root & Containers):** `HomeRootView`, `BottomControls`, `MealPanel` entegre edildi.
*   **Grup C (Header & Loop):** `CurrentGlucoseView` (şekiller, renkler), `PumpView`, `LoopView` yazıldı.
*   **Grup E (Grafikler - Vico):** 
    *   `MainChartView.kt` Vico kütüphanesi ile sıfırdan oluşturuldu. 
    *   Grafik üzerinde parmak kaydırma (Scrub) işlemi ile 600ms'lik bir **decay (gecikmeli kapanma) zamanlayıcısı** kurularak `MealPanel` (Readout) modu aktif edildi.
    *   Tıpkı iOS'taki `ChartElements` klasörü gibi, Android tarafında da kalan 10 adet grafik katmanının (Basal, Insulin, Carb, Forecast vb.) Vico Decoration ve LineSpec karşılıkları `elements` klasöründe prototiplendi.

---

## ⚠️ 3. KARŞILAŞILAN SORUNLAR VE ÇÖZÜMLERİ

1.  **Vico'nun Snap Yan Etkisi (Scrubbing Hatası):**
    *   *Sorun:* Vico, dokunulan noktada veri yoksa marker'ı otomatik olarak "en yakın" veriye yapıştırıyordu. Bu durum tahmin (Cone) bölgesinde yanlış glikoz değerinin okunmasına sebep oldu.
    *   *Çözüm:* `combinedEntries.lastOrNull()` üzerinden son gerçek veri noktasının X koordinatı tespit edildi. Dokunulan yer bu koordinatı geçiyorsa `isReal = false` bayrağı ile MealPanel'in son gerçek değerde "donması" sağlandı.
2.  **PointerInput & Coroutine UI Donması:**
    *   *Sorun:* Kullanıcı grafikte parmağını kaydırırken saniyede onlarca kez onDrag tetikleniyor, bu da animasyon Job'larının birikmesine ve UI'ın takılmasına neden oluyordu.
    *   *Çözüm:* Her yeni hareket olayında mevcut Job'ı `cancel()` edip yepyeni bir `launch(delay(600))` başlatan tekil bir `Job` referansı (`DecayJobHolder`) mimarisi kuruldu. Performans sızıntısı kalıcı olarak önlendi.
3.  **Diskte Yer Kalmaması (0 B) ve Build Hataları:**
    *   *Sorun:* Proje derlenirken diskin tamamen dolduğu (0 B) tespit edildi ve dosyalar yazılamadı.
    *   *Çözüm:* Kullanılmayan ~3.2 GB'lık eski Android Studio kurulum exe'si ve x86_64 emülatör zip dosyaları Masaüstünden silinerek alan açıldı.
4.  **Kapsam Dışı Bırakılan iOS Özellikleri:**
    *   `ContactImage.swift` (SiriKit INSendMessageIntent) ve `LiveActivity` (Dynamic Island) özellikleri Android'in doğasına (System API) uymadığı için bilinçli olarak atlandı/daha sonra widget olarak ele alınacak.

---

## 📜 4. KESİN KURALLAR VE PROTOKOLLER

Tüm geliştirme süreci boyunca yapay zeka (ajan) ve kullanıcının uyması gereken katı kurallar:

1.  **Klinik İzlenim Kuralı:** Asla sahte veya yanıltıcı bir veriyi gerçekmiş gibi çizme. Tahmin koridorunda merkez çizgi yoksa, oraya "güzel görünsün" diye uydurma bir merkez çizgi ekleme. Glikoz verisi tıbbi bir veridir.
2.  **Görsel Kanıt Protokolü:** Ajanın (AI) kendi ortamında ekranı veya emülatörü yoktur. Ajan kodu yazar, hatasız derlenmesini sağlar. Ancak UI'ın **"Trio gibi hissettirdiğine"** dair nihai onay, KULLANICININ kendi cihazında alacağı ekran görüntüleri ve bizzat testleri ile verilir.
3.  **A-E Raporlama Formatı:** Her 50 satırdan büyük dosyanın çevirisinde; A(Kaynak Analizi), B(UI/UX Planı), C(ViewModel Entegrasyonu), D(Jetpack Compose Kodu), E(Test Beklentisi) formatına harfiyen uyulur.
4.  **Orijinal Mantığa Sadakat:** iOS (Swift) tarafındaki mantıklar, eğer Android'de çok ciddi bir performans kısıtı yoksa, **olduğu gibi (1:1 parite ile)** Kotlin'e aktarılır. `HomeViewModel` içindeki durum geçişleri ve hesaplamalar Swift kodunun birebir aynısı olmak zorundadır.

---
*Son Güncelleme: 24 Eylül 2026*
