# Catatan Minggu 2: Struktur OOP (Class, Object, Encapsulation)

## Yang saya kerjakan
Saya melengkapi class `Aset` di paket `model`. Isinya empat field private (`kode`, `nama`, `status`, `kondisi`), konstruktor `Aset(kode, nama)`, getter untuk semua field, dan setter untuk `nama`, `status`, dan `kondisi`. Status awal `TERSEDIA` dan kondisi awal `BAIK`. Method `bisaDipinjam`, `getJenis`, dan `hitungDenda` tidak saya ubah karena jatahnya minggu 3 sampai 5.

## Keputusan desain
- `kode` dibuat `private final` dan tidak punya setter, karena kode adalah identitas aset yang tidak boleh berubah.
- Validasi teks ditulis sekali di method private `wajibIsi`, dipakai oleh konstruktor dan `setNama`. Null, kosong, dan string berisi spasi saja (`isBlank`) ditolak dengan `IllegalArgumentException`.
- `setStatus` dan `setKondisi` menolak null. Pengecekan dilakukan sebelum nilai diubah, jadi kalau ditolak nilai lama tetap aman.
- Kode dan nama tidak dinormalisasi (tanpa trim atau huruf besar), supaya sederhana dan data tidak berubah diam-diam. Keputusan ini saya catat di `docs/asumsi.md`.

## Kelebihan encapsulation pada sistem peminjaman aset
- Aturan ada di satu tempat: aset tidak mungkin punya kode kosong atau status null karena semua jalur masuk lewat validasi.
- Mudah dirawat: kalau aturan nama berubah, cukup ubah satu method, dan class lain tidak ikut rusak.
- Keterbatasannya: kodenya lebih panjang dibanding field public, untuk hal yang kelihatannya sederhana.

## Kendala
Awalnya saya pakai `isEmpty()` untuk mengecek kode dan nama, padahal tes juga menguji string berisi spasi saja, jadi saya ganti dengan `isBlank()`. Saya juga sempat memastikan nama lama tetap utuh saat `setNama("")` ditolak, dengan menaruh validasi sebelum field diubah. Maven tidak bisa mengunduh dependensi di lingkungan tanpa internet, jadi saya cek dulu dengan `javac` lalu menjalankan `bash scripts/uji.sh` di komputer sendiri.

## Penggunaan AI
- Alat yang dipakai: Claude
- Bagian yang dibantu AI: field, konstruktor, getter/setter, dan method `wajibIsi` pada `Aset`, serta draf catatan ini
- Prompt utama: kerjakan week2 dan saya bisa memahami
- Satu hal yang saya pahami lebih baik sekarang: kenapa validasi ditaruh di setter dan konstruktor, bukan di luar class, dan kenapa `kode` dibuat `final`.