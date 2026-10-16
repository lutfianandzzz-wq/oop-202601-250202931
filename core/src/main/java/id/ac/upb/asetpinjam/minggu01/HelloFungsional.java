package id.ac.upb.asetpinjam.minggu01;
import java.util.function.Function;

/** Minggu 1, gaya FUNGSIONAL: fungsi sebagai nilai (lambda), tanpa state yang berubah. */
public final class HelloFungsional {

    private HelloFungsional() {
    }

    public static Function<String, String> pembuatSapaan(String nim) {
        return nama -> "Hello Aset-Pinjam, saya " + nama + " (" + nim + ")";
    }

    public static void main(String[] args) {
        System.out.println(pembuatSapaan("25234567").apply("yanto"));
    }
}