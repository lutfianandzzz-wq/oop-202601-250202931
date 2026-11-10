# Catatan Minggu 5: Abstraction

## Yang saya kerjakan
Saya mengisi `Aset.bisaDipinjam()` agar bernilai true hanya jika status TERSEDIA dan kondisi bukan RUSAK_BERAT, lalu mengubah `Aset` menjadi `abstract class` dengan `getJenis()` dan `hitungDenda(int)` sebagai method abstrak. Di paket `denda`, `DendaFlat` saya implementasikan sebagai `aset.hitungDenda(hari)`, dan `DendaProgresif` memakai rumus sendiri: 7 hari pertama memakai tarif biasa, hari ke-8 dan seterusnya dikenai dua kali tarif biasa. Keduanya mengimplementasikan interface `AturanDenda`.

## Keputusan desain
- Rumus progresif adalah keputusan saya dan saya catat sebagai A-09 di `docs/asumsi.md`. Tarif biasa diambil dari `aset.hitungDenda(...)`, jadi pengali tiap jenis (Ruang 1x, Laptop 2x, AlatLab 4x) dan tarif varian otomatis ikut terpakai tanpa saya menyalin angka itu.
- `Aset` dijadikan abstract karena aset yang "umum" tidak pernah ada di dunia nyata, yang ada selalu ruang, laptop, atau alat lab. Dengan abstract, `new Aset(...)` tidak bisa dibuat lagi dan setiap subclass wajib mengimplementasikan `getJenis()` dan `hitungDenda`.
- `getJenis()` dan `hitungDenda` dibuat abstrak sedangkan `bisaDipinjam()` tetap konkret di `Aset`. Aturan `bisaDipinjam()` sama untuk semua jenis, sehingga cukup ditulis sekali di induk, sedangkan jenis dan denda berbeda untuk tiap subclass.
- Aturan denda dipisah dari `Aset` ke interface `AturanDenda`. Alasannya, cara menghitung denda (flat atau progresif) bisa berganti-ganti tanpa mengubah kelas aset, dan satu aset tidak perlu tahu aturan mana yang dipakai.

## Abstract class dan interface
- Abstract class (`Aset`) cocok untuk keluarga kelas yang punya state bersama (`kode`, `nama`, `status`, `kondisi`) dan perilaku bersama (`bisaDipinjam`). Satu kelas hanya boleh mewarisi satu abstract class.
- Interface (`DapatDipinjam`, `AturanDenda`) cocok untuk kemampuan atau kontrak yang bisa dimiliki kelas-kelas yang tidak sekeluarga. Satu kelas boleh mengimplementasikan banyak interface, dan interface tidak menyimpan state.
- Di proyek ini, `Aset` mengimplementasikan `DapatDipinjam` sekaligus menjadi abstract class, jadi keduanya dipakai bersamaan dengan peran berbeda.

## Kendala
Saya sempat bingung kenapa `getJenis()` boleh dibuat abstrak padahal soal hanya menyebut `hitungDenda`. Ternyata ketiga subclass sudah meng-override `getJenis()` sejak minggu 3, jadi membuatnya abstrak aman dan lebih tegas, karena kompilator akan menolak subclass baru yang lupa menuliskannya. Untuk rumus progresif, saya memeriksa tiga syarat tes satu per satu: 0 hari menghasilkan 0, tidak pernah lebih murah dari flat, dan tidak pernah turun. Rumus saya memenuhi ketiganya, dan pada hari ke-30 denda progresif lebih mahal dari flat (misalnya Ruang Rp 397.500 berbanding Rp 225.000). Setelah itu saya menjalankan `.\scripts\uji.ps1 -Minggu 5` untuk memeriksa hasilnya.

## Penggunaan AI
- Alat yang dipakai: Claude
- Bagian yang dibantu AI: `bisaDipinjam()` dan perubahan `Aset` menjadi abstract, `DendaFlat`,dan `DendaProgresif`.
- Satu hal yang saya pahami lebih baik sekarang: kapan memilih abstract class dan kapan interface, kenapa method abstrak memaksa subclass mengimplementasikannya, dan bagaimana pola `AturanDenda` memisahkan cara menghitung denda dari kelas aset.