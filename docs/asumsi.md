# Asumsi dan Keputusan Desain

Satu entri per keputusan. Entri yang menyangkut minggu setelah minggu 2 masih berupa rencana, jadi kolom
"Dampak ke kode" dan "Tes yang membuktikan" diisi saat minggunya dikerjakan.

Varian saya (NIM 250202931, digit terakhir 1): tarif denda Rp 7.500 per hari, batas durasi ruang 6 jam, kuota laptop 2.

## Aturan ambigu dari docs/aturan-bisnis.md

## A-01: batas waktu bentrok
- Pertanyaan: 09:00-11:00 dan 11:00-13:00 pada aset yang sama bentrok atau tidak?
- Keputusan: Tidak bentrok. Rentang dihitung setengah terbuka: mulai termasuk, selesai tidak termasuk.
- Alasan: Aset yang dikembalikan tepat pukul 11:00 bisa langsung dipinjam orang berikutnya, sehingga jadwal tidak terbuang.
- Dampak ke kode: `JadwalPeminjaman` (minggu 7). Dua peminjaman bentrok jika `mulaiA < selesaiB && mulaiB < selesaiA`.
- Tes yang membuktikan: `BatasBentrokTest.selesaiSamaDenganMulai` (diisi minggu 7).

## A-02: terlambat 1 menit atau 25 jam
- Pertanyaan: Bagaimana denda dihitung bila keterlambatan bukan kelipatan hari?
- Keputusan: Keterlambatan dibulatkan ke atas ke hari penuh. 1 menit dihitung 1 hari, 25 jam dihitung 2 hari, tepat 0 tidak kena denda.
- Alasan: Aturan sederhana dan mudah dijelaskan ke peminjam, dan tetap mendorong pengembalian tepat waktu.
- Dampak ke kode: `hitungDenda(int hariTerlambat)` tetap menerima hari bulat. Pembulatan dilakukan oleh pemanggil sebelum `hitungDenda` dipanggil (minggu 4 dan minggu 7).
- Tes yang membuktikan: tes pembulatan di folder `mahasiswa/` (diisi minggu 7).

## A-03: pinjam aset yang sama dua kali pada hari yang sama
- Pertanyaan: Bolehkah satu peminjam meminjam aset yang sama dua kali dalam sehari?
- Keputusan: Boleh, selama rentang waktunya tidak bentrok (aturan A-01).
- Alasan: Tidak ada alasan bisnis untuk melarang, misalnya ruang dipakai pagi lalu sore. Pembatasan sudah diatur oleh aturan bentrok dan kuota.
- Dampak ke kode: `JadwalPeminjaman.ajukan` tidak menambah pengecekan khusus peminjam kembar.
- Tes yang membuktikan: tes di folder `mahasiswa/` (diisi minggu 7).

## A-04: kondisi kembali lebih buruk tanpa saksi
- Pertanyaan: Siapa menanggung biaya bila kondisi aset saat kembali lebih buruk dan tidak ada saksi?
- Keputusan: Peminjam menanggung, dengan acuan kondisi yang dicatat petugas saat serah terima. Jika saat serah terima sudah tercatat rusak, peminjam tidak ditanggung.
- Alasan: Selama dipinjam aset ada dalam penguasaan peminjam, dan catatan petugas saat serah terima menjadi bukti netral.
- Dampak ke kode: kondisi dicatat saat serah terima dan pengembalian (`setKondisi`). Penanganan biaya belum dibuat dan masuk catatan pengembangan.
- Tes yang membuktikan: belum ada (di luar cakupan tes mingguan).

## A-05: pengajuan disetujui, aset rusak sebelum serah terima
- Pertanyaan: Apa yang terjadi pada pengajuan DISETUJUI bila aset dinyatakan rusak sebelum serah terima?
- Keputusan: Pengajuan dibatalkan (`DISETUJUI -> DIBATALKAN`, perpindahan yang sah) dan serah terima tidak bisa dilakukan.
- Alasan: Aset rusak tidak layak dipinjam, dan perpindahan status itu sudah tersedia di aturan pasti.
- Dampak ke kode: `setStatus(RUSAK)` membuat `bisaDipinjam()` bernilai false. Pengecekan saat serah terima memakai `bisaDipinjam()` (minggu 5 dan 9).
- Tes yang membuktikan: tes di folder `mahasiswa/` (diisi minggu 9).

## A-06: pembatalan mendadak dan DIAJUKAN langsung DIBATALKAN
- Pertanyaan: Apakah pembatalan kurang dari 1 jam sebelum mulai dikenai sanksi? Bolehkah DIAJUKAN langsung DIBATALKAN?
- Keputusan: Tidak ada sanksi denda untuk pembatalan mendadak. DIAJUKAN langsung DIBATALKAN tidak boleh dan melempar `StatusTidakSahException`.
- Alasan: Denda dalam sistem hanya untuk keterlambatan. Tabel perpindahan status di aturan pasti hanya mengizinkan DIAJUKAN ke DISETUJUI atau DITOLAK.
- Dampak ke kode: `Peminjaman.ubahStatus` mengikuti tabel perpindahan (minggu 9).
- Tes yang membuktikan: tes di folder `mahasiswa/` (diisi minggu 9).

## A-07: status DITOLAK, DIBATALKAN, dan DIAJUKAN terhadap jadwal dan kuota
- Pertanyaan: Apakah DITOLAK atau DIBATALKAN masih menempati jadwal? Apakah DIAJUKAN dihitung dalam kuota?
- Keputusan: DITOLAK dan DIBATALKAN tidak menempati jadwal. DIAJUKAN, DISETUJUI, DIPINJAM, dan TERLAMBAT menempati jadwal. Kuota laptop hanya menghitung DISETUJUI dan DIPINJAM.
- Alasan: Peminjaman yang sudah batal atau ditolak tidak perlu menghalangi orang lain. Kuota mengikuti aturan pasti nomor 5, sedangkan DIAJUKAN tetap menempati jadwal agar dua pengajuan tidak saling bertabrakan.
- Dampak ke kode: `JadwalPeminjaman` (minggu 7).
- Tes yang membuktikan: tes di folder `mahasiswa/` (diisi minggu 7).

## Asumsi lain

## A-08: normalisasi kode dan nama aset
- Pertanyaan: Apakah kode dan nama aset di-trim dan diubah ke huruf besar?
- Keputusan: Tidak. Nilai disimpan persis seperti yang dimasukkan. Yang ditolak hanya null, kosong, dan string berisi spasi saja.
- Alasan: Lebih sederhana, dan data tidak berubah diam-diam tanpa sepengetahuan pemanggil.
- Dampak ke kode: konstruktor `Aset` dan method private `wajibIsi` memakai `isBlank()`, tanpa trim atau uppercase.
- Tes yang membuktikan: `Minggu02AsetTest.menyimpanKodeDanNamaDenganNilaiAwal` dan `Minggu02AsetTest.kodeKosongDitolak`.