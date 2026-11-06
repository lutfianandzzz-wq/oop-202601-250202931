# Catatan Minggu 4: Polymorphism

## Yang saya kerjakan
Saya meng-override `hitungDenda(int)` di tiga subclass `Aset`: `Ruang` (pengali 1x), `Laptop` (2x), dan `AlatLab` (4x). Rumus dendanya hari terlambat x pengali jenis x tarif varian, dan tarif dibaca dari `Varian.aktif().dendaPerHari`. Tarif varian saya Rp 7.500, jadi Ruang 3 hari = 22.500, Laptop 3 hari = 45.000, dan AlatLab 2 hari = 60.000. Nol hari menghasilkan 0 dan hari negatif ditolak dengan `IllegalArgumentException`. Di `KatalogAset` saya mengerjakan dua overload `cari`: `cari(kata)` mencari nama yang memuat kata tanpa membedakan huruf besar atau kecil, dan `cari(kata, jenis)` melakukan hal yang sama tetapi hanya untuk jenis yang diminta.

## Keputusan desain
- Rumus dasar saya taruh di satu method `protected static dendaDasar(int, int)` di `Aset`, lalu tiap subclass hanya memanggilnya dengan pengali masing-masing. Dengan begitu aturan "hari negatif ditolak" dan perkalian tarif cukup ditulis sekali, bukan tiga kali.
- `hitungDenda` di `Aset` tetap berisi `throw UnsupportedOperationException`, karena di minggu 5 `Aset` menjadi abstract dan method ini ikut abstract. Setiap subclass memakai `@Override` agar kompilator memeriksa bahwa signature-nya benar-benar sama dengan milik induk.
- Tipe kembalian dan perhitungan memakai `long` (`(long) hari * pengali * tarif`) supaya tidak overflow untuk denda yang besar.
- `cari(kata, jenis)` memakai ulang `cari(kata)` lalu menyaring hasilnya berdasarkan `getJenis()`, sehingga aturan pencocokan nama hanya ada di satu tempat.

## Overriding dan overloading
- Overriding: `hitungDenda` ditulis ulang di tiap subclass dengan perilaku berbeda tetapi signature sama. Saat kode memanggil `aset.hitungDenda(3)` lewat variabel bertipe `Aset`, Java memilih versi sesuai objek aslinya (dynamic binding), dipilih saat program berjalan.
- Overloading: `cari(String)` dan `cari(String, JenisAset)` memakai nama sama tetapi parameter berbeda. Java memilih versinya saat kompilasi, berdasarkan parameter yang dikirim.
- Perbedaan utamanya: overriding terjadi antara induk dan subclass dengan signature sama, sedangkan overloading terjadi dalam satu kelas dengan parameter berbeda.

## Kendala
Awalnya saya menempel `dendaDasar` di `Aset` dan tanpa sengaja menimpa method `hitungDenda` lama, sehingga `@Override` di `Ruang` bergaris merah karena Java tidak menemukan method di kelas induk. Saya mengembalikan `hitungDenda` di `Aset`, dan garis merahnya hilang. Pada `KatalogAset`, `JenisAset` sempat bergaris merah karena baris import-nya ikut terhapus waktu mengganti method, jadi saya menambahkannya lagi. File tes `Minggu04PolimorfismeTest.java` sempat bertanda M, tetapi file itu milik dosen sehingga saya kembalikan dengan `git restore`. Setelah itu saya menjalankan `.\scripts\uji.ps1 -Minggu 4` untuk memeriksa hasilnya.

## Penggunaan AI
- Alat yang dipakai: Claude
- Bagian yang dibantu AI: method `dendaDasar` di `Aset`, override `hitungDenda` di `Ruang`, `Laptop`, dan `AlatLab`, dua overload `cari` di `KatalogAset`, serta draf catatan ini
- Satu hal yang saya pahami lebih baik sekarang: kenapa `@Override` mewajibkan method yang sama ada di kelas induk, bagaimana dynamic binding memilih versi `hitungDenda` berdasarkan objek aslinya, dan apa beda overriding dengan overloading.