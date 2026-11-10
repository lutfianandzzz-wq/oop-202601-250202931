package id.ac.upb.asetpinjam.model;

import id.ac.upb.asetpinjam.config.Varian;
import id.ac.upb.asetpinjam.kontrak.DapatDipinjam;

/**
 * Aset yang dapat dipinjam.
 *
 * <p>Minggu 2: lengkapi field, konstruktor, getter/setter, dan validasi (encapsulation).
 * Minggu 3: tambahkan getJenis() di tiap subclass.
 * Minggu 4: hitungDenda() dioverride di tiap subclass.
 * Minggu 5: isi bisaDipinjam() dan ubah menjadi class abstrak
 * (TestSupport.aset(...) di folder test menyesuaikan sendiri, lihat docs/minggu-05.md).
 */
public abstract class Aset implements DapatDipinjam {
    
    private final String kode;
    private String nama;
    private StatusAset status = StatusAset.TERSEDIA;
    private Kondisi kondisi = Kondisi.BAIK;

    /** Kode dan nama tidak boleh null atau kosong (IllegalArgumentException). */
    public Aset(String kode, String nama) {
        this.kode = wajibIsi(kode, "kode");
        this.nama = wajibIsi(nama, "nama");
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public StatusAset getStatus() {
        return status;
    }

    public Kondisi getKondisi() {
        return kondisi;
    }

    /** Nama baru tidak boleh kosong. Bila ditolak, nama lama tetap dipakai. */
    public void setNama(String nama) {
        this.nama = wajibIsi(nama, "nama");
    }

    /** Status tidak boleh null. */
    public void setStatus(StatusAset status) {
        if (status == null) {
            throw new IllegalArgumentException("status tidak boleh null");
        }
        this.status = status;
    }

    /** Kondisi tidak boleh null. */
    public void setKondisi(Kondisi kondisi) {
        if (kondisi == null) {
            throw new IllegalArgumentException("kondisi tidak boleh null");
        }
        this.kondisi = kondisi;
    }

    protected static String wajibIsi(String nilai, String namaField) {
        if (nilai == null || nilai.isBlank()) {
            throw new IllegalArgumentException(namaField + " tidak boleh null atau kosong");
        }
        return nilai;
    }

    /** TODO minggu 5: true hanya jika status TERSEDIA dan kondisi bukan RUSAK_BERAT. */
       /** True hanya jika status TERSEDIA dan kondisi bukan RUSAK_BERAT. */
    @Override
    public boolean bisaDipinjam() {
        return status == StatusAset.TERSEDIA && kondisi != Kondisi.RUSAK_BERAT;
    }
       
        /** Jenis aset. Tiap subclass wajib mengembalikan jenisnya sendiri. */
    public abstract JenisAset getJenis();

        /** Rumus bersama (minggu 4): hari x multiplier x tarif varian. Hari negatif ditolak. */
    protected static long dendaDasar(int hariTerlambat, int multiplier) {
    if (hariTerlambat < 0) {
        throw new IllegalArgumentException("hari terlambat tidak boleh negatif");
    }
    return (long) hariTerlambat * multiplier * Varian.aktif().dendaPerHari;
    }

    /** Denda keterlambatan. Dioverride tiap subclass (aturan di docs/aturan-bisnis.md). */
    public abstract long hitungDenda(int hariTerlambat);
    
}