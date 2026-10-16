# Catatan Minggu 1: Paradigma Pemrograman dan Setup

## Yang saya kerjakan
Saya melengkapi `HelloProsedural`, `HelloOOP`, dan `HelloFungsional` di paket `minggu01`. Ketiganya menghasilkan sapaan yang sama: "Hello Aset-Pinjam, saya yanto (25234567)". Saya juga mengisi `mahasiswa.properties` dengan nama dan NIM, serta menyiapkan JDK 21, Maven, Git, dan repo.

## Keputusan desain
- Bentuk sapaan: `Hello Aset-Pinjam, saya <nama> (<nim>)`, mengikuti contoh dosen. Format ini sama di ketiga gaya agar mudah dibandingkan.
- Prosedural: method static `sapa(nama, nim)`, data lewat parameter.
- OOP: field `private final` nama dan nim, diisi lewat konstruktor, dipakai oleh `sapa()`.
- Fungsional: `pembuatSapaan(nim)` mengembalikan lambda `nama -> ...`, dipanggil dengan `.apply(nama)`.

## Kelebihan dan keterbatasan tiap gaya (sistem peminjaman aset)
- Prosedural
  - Kelebihan: sederhana dan cepat dibuat, cocok untuk tugas kecil seperti memformat teks.
  - Keterbatasan: data dan aturan tersebar di banyak fungsi, sulit dirawat saat aset, peminjam, dan denda makin banyak.
- OOP
  - Kelebihan: data dan perilaku satu tempat (misalnya Aset, Peminjaman), mudah dikembangkan dan diuji.
  - Keterbatasan: butuh lebih banyak kode dan perancangan kelas untuk hal yang sederhana.
- Fungsional
  - Kelebihan: tanpa state yang berubah, sehingga aman dan mudah diuji, cocok untuk aturan seperti hitung denda.
  - Keterbatasan: kurang natural untuk menyimpan kondisi aset yang terus berubah (tersedia atau dipinjam), dan lebih sulit dipahami pemula.

## Kendala
Maven belum dikenali karena belum ada di PATH, dan Java di komputer masih versi 26. Saya memasang JDK 21, memperbaiki PATH Maven, lalu mengecek dengan `java -version` dan `mvn -v`. Di kode, saya sempat salah menaruh `main` di `HelloFungsional` dan kurang field di `HelloOOP`, lalu saya perbaiki.

## Penggunaan AI
- Alat yang dipakai: Claude
- Bagian yang dibantu AI: setup Maven dan JDK, isi `HelloProsedural.sapa`, `HelloOOP` (field, konstruktor, `sapa`), dan `HelloFungsional.pembuatSapaan`
- Prompt utama: "kerjakan secara singkat dan saya bisa menjelaskan" (dengan menempelkan kode TODO minggu 1)
- Yang saya ubah atau tolak dari saran AI, dan alasannya: saya menulis nama dan NIM langsung di `main` agar mudah dicoba, dan memeriksa serta memperbaiki sendiri kode yang salah ketik.
- Satu hal yang saya pahami lebih baik sekarang: perbedaan data yang disimpan di field objek (OOP) dengan data yang terbawa di dalam lambda (fungsional).