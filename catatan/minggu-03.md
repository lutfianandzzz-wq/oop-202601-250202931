# Catatan Minggu 3: Inheritance

## Yang saya kerjakan
Saya melengkapi tiga subclass dari `Aset` di paket `model`: `Ruang` (field `kapasitas`), `Laptop` (field `spesifikasi` dan `nomorInventaris`), dan `AlatLab` (field `laboratorium` dan `tingkatRisiko`). Tiap konstruktor memanggil `super(kode, nama)` lebih dulu, lalu memvalidasi field miliknya sendiri. Tiap subclass juga meng-override `getJenis()` agar mengembalikan `RUANG`, `LAPTOP`, atau `ALAT_LAB`.

## Keputusan desain
- Field yang dimiliki semua aset (`kode`, `nama`, `status`, `kondisi`) tetap di `Aset`, sedangkan field yang hanya masuk akal untuk satu jenis ada di subclass. Kapasitas tidak ditaruh di `Aset` karena laptop tidak punya kapasitas.
- Field subclass dibuat `private final` tanpa setter, karena tidak ada aturan yang membolehkan nilainya berubah setelah objek dibuat.
- `Aset.wajibIsi` saya ubah dari `private` menjadi `protected static` supaya `Laptop` dan `AlatLab` memakai ulang aturan "tidak boleh null atau kosong" tanpa menyalin kodenya. Perilakunya tidak berubah, jadi tes minggu 2 tetap berlaku.
- Kapasitas ruang dicek dengan `<= 0`, jadi 0 dan angka negatif sama-sama ditolak dengan `IllegalArgumentException`. `tingkatRisiko` pada `AlatLab` juga ditolak bila null.
- `getJenis()` di `Aset` sengaja tidak saya ubah. Di minggu 5 `Aset` menjadi abstract, dan saat itu method ini ikut menjadi abstract.

## Kelebihan dan keterbatasan inheritance pada sistem peminjaman aset
- Kelebihan: kode, nama, status, dan kondisi cukup ditulis sekali di `Aset`. Validasinya ikut berlaku untuk semua jenis, dan `List<Aset>` bisa berisi ruang, laptop, dan alat lab sekaligus.
- Keterbatasan: subclass terikat pada induknya, jadi perubahan di `Aset` bisa merusak ketiga subclass sekaligus.
- Contoh yang kurang tepat memakai inheritance: `TingkatRisiko` pada `AlatLab`. Risiko adalah sifat yang bisa berubah, sedangkan hubungan inheritance ditetapkan saat kode ditulis. Kalau dibuat subclass per tingkat risiko (`AlatLabRisikoTinggi`, `AlatLabRisikoSedang`, dan seterusnya), kodenya kaku dan tidak bisa diganti ketika program berjalan. Komposisi, yaitu `AlatLab` memegang objek aturan risiko yang bisa diganti, lebih cocok.

## Kendala
Awalnya `wajibIsi` masih `private`, sehingga `Laptop` dan `AlatLab` tidak bisa memakainya dan saya hampir menulis ulang pengecekan yang sama di tiap kelas. Saya mengubahnya menjadi `protected` supaya aturannya tetap di satu tempat. Saya juga sempat bingung kenapa `super(kode, nama)` harus di baris pertama konstruktor. Ternyata Java membangun bagian induk lebih dulu, dan itu menjamin validasi kode dan nama berjalan sebelum validasi field subclass, sehingga `new Ruang("", "Aula", 10)` gagal karena kode kosong, bukan karena kapasitas. Maven tidak bisa mengunduh dependensi di lingkungan tanpa internet, jadi saya cek dulu dengan pengujian manual lalu menjalankan `bash scripts/uji.sh 3` di komputer sendiri.

## Penggunaan AI
- Alat yang dipakai: Claude
- Bagian yang dibantu AI: konstruktor, getter, dan `getJenis()` pada `Ruang`, `Laptop`, dan `AlatLab`, perubahan `wajibIsi` menjadi `protected` di `Aset`
- Satu hal yang saya pahami lebih baik sekarang: kenapa `super(...)` harus dipanggil pertama, bagaimana validasi di `Aset` tetap berlaku untuk subclass, dan kenapa risiko alat lab kurang cocok dibuat sebagai subclass.