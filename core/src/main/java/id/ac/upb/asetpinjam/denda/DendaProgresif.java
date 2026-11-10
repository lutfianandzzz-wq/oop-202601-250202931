package id.ac.upb.asetpinjam.denda;

import id.ac.upb.asetpinjam.kontrak.AturanDenda;
import id.ac.upb.asetpinjam.model.Aset;

/**
 * Minggu 5: denda yang makin berat bila makin lama terlambat.
 *
 * <p>Rumus (asumsi A-09): 7 hari pertama memakai tarif biasa, hari ke-8 dan seterusnya dikenai
 * dua kali tarif biasa. Tarif biasa diambil dari aset.hitungDenda(...), sehingga tetap mengikuti
 * pengali tiap jenis aset dan tarif varian.
 */
public class DendaProgresif implements AturanDenda {

    /** Jumlah hari pertama yang masih memakai tarif biasa. */
    private static final int BATAS_HARI_BIASA = 7;

    /** Pengali tarif untuk hari di atas batas. */
    private static final int PENGALI_LEWAT_BATAS = 2;

    @Override
    public long hitung(Aset aset, int hariTerlambat) {
        if (hariTerlambat <= BATAS_HARI_BIASA) {
            // Termasuk 0 hari (hasil 0) dan hari negatif (ditolak oleh aset.hitungDenda).
            return aset.hitungDenda(hariTerlambat);
        }
        long denganTarifBiasa = aset.hitungDenda(BATAS_HARI_BIASA);
        long hariLebih = hariTerlambat - BATAS_HARI_BIASA;
        return denganTarifBiasa + PENGALI_LEWAT_BATAS * aset.hitungDenda((int) hariLebih);
    }
}