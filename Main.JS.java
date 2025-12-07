
import java.util.*;

// Model (Encapsulation)
class Mahasiswa {
    private String nama;
    private int nim;
    private String jurusan;

    public Mahasiswa(String nama, int nim, String jurusan) {
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getNim() { return nim; }
    public void setNim(int nim) { this.nim = nim; }

    public String getJurusan() { return jurusan; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }

    @Override
    public String toString() {
        return String.format("NIM: %d | Nama: %s | Jurusan: %s", nim, nama, jurusan);
    }
}

// Service/Manager
class MahasiswaManager {
    private final List<Mahasiswa> data = new ArrayList<>();

    public void tambah(Mahasiswa m) {
        // Cek NIM duplikat
        if (cariByNim(m.getNim()) != null) {
            System.out.println("NIM sudah ada. Gunakan NIM lain.");
            return;
        }
        data.add(m);
        System.out.println("✅ Data ditambahkan.");
    }

    public void list() {
        if (data.isEmpty()) {
            System.out.println("(Belum ada data)");
            return;
        }
        data.forEach(System.out::println);
    }

    public boolean update(int nim, String namaBaru, String jurusanBaru) {
        Mahasiswa m = cariByNim(nim);
        if (m == null) return false;
        if (namaBaru != null && !namaBaru.isBlank()) m.setNama(namaBaru);
        if (jurusanBaru != null && !jurusanBaru.isBlank()) m.setJurusan(jurusanBaru);
        return true;
    }

    public boolean delete(int nim) {
        Mahasiswa m = cariByNim(nim);
        if (m == null) return false;
        return data.remove(m);
    }

    private Mahasiswa cariByNim(int nim) {
        for (Mahasiswa m : data) {
            if (m.getNim() == nim) return m;
        }
        return null;
    }
}

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final MahasiswaManager manager = new MahasiswaManager();

    private static void menu() {
        System.out.println("\n=== MENU MAHASISWA ===");
        System.out.println("1. Tambah");
        System.out.println("2. Lihat");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("5. Keluar");
        System.out.print("Pilih: ");
    }

    public static void main(String[] args) {
        // Seed data contoh
        manager.tambah(new Mahasiswa("Andi", 12345, "Informatika"));
        manager.tambah(new Mahasiswa("Budi", 67890, "Sistem Informasi"));

        while (true) {
            menu();
            String pilih = in.nextLine().trim();

            switch (pilih) {
                case "1": tambah(); break;
                case "2": manager.list(); break;
                case "3": update(); break;
                case "4": delete(); break;
                case "5": System.out.println("Bye!"); return;
                default: System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tambah() {
        try {
            System.out.print("NIM: "); int nim = Integer.parseInt(in.nextLine().trim());
            System.out.print("Nama: "); String nama = in.nextLine();
            System.out.print("Jurusan: "); String jurusan = in.nextLine();
            manager.tambah(new Mahasiswa(nama, nim, jurusan));
        } catch (Exception e) {
            System.out.println("Input tidak valid.");
        }
    }

    private static void update() {
        try {
            System.out.print("Masukkan NIM yang akan diupdate: ");
            int nim = Integer.parseInt(in.nextLine().trim());
            System.out.print("Nama baru (kosongkan jika tidak diubah): ");
            String nama = in.nextLine();
            System.out.print("Jurusan baru (kosongkan jika tidak diubah): ");
            String jurusan = in.nextLine();
            boolean ok = manager.update(nim, nama, jurusan);
            System.out.println(ok ? "✅ Data diupdate." : "❌ NIM tidak ditemukan.");
        } catch (Exception e) {
            System.out.println("Input tidak valid.");
        }
    }

    private static void delete() {
        try {
            System.out.print("Masukkan NIM yang akan dihapus: ");
            int nim = Integer.parseInt(in.nextLine().trim());
            boolean ok = manager.delete(nim);
            System.out.println(ok ? "✅ Data dihapus." : "❌ NIM tidak ditemukan.");
        } catch (Exception e) {
            System.out.println("Input tidak valid.");
        }
    }
}
