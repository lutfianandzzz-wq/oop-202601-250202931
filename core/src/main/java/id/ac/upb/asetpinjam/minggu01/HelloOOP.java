package id.ac.upb.asetpinjam.minggu01;

/** Minggu 1, gaya OOP: data dan perilaku dibungkus dalam sebuah class. */
public class HelloOOP {

    private final String nama;
    private final String nim;

    public HelloOOP(String nama, String nim) {
        this.nama = nama;
        this.nim = nim;
    }

    public String sapa() {
        return "Hello Aset-Pinjam, saya " + nama + " (" + nim + ")";
    }

    public static void main(String[] args) {
        System.out.println(new HelloOOP("yanto", "25234567").sapa());
    }
}