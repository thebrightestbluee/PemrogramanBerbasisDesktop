import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Menu {
    String nama;
    double harga;
    String kategori;

    Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

public class Main {
    private static final double BIAYA_PELAYANAN = 20000;
    private static final double TARIF_PAJAK = 0.10;
    private static final double BATAS_DISKON = 100000;
    private static final double BATAS_TAWARAN_MINUMAN = 50000;
    private static final double TARIF_DISKON = 0.10;

    private static List<Menu> menuMakanan = new ArrayList<>();
    private static List<Menu> menuMinuman = new ArrayList<>();

    public static void main(String[] args) {
        // Inisialisasi menu
        inisialisasiMenu();
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Pemesanan");
            System.out.println("2. Manajemen Menu");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // membersihkan newline

            switch (pilihan) {
                case 1:
                    tampilkanMenu();
                    prosesPesanan(scanner);
                    break;
                case 2:
                    manajemenMenu(scanner);
                    break;
                case 3:
                    System.out.println("Terima kasih! Sampai jumpa.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void inisialisasiMenu() {
        menuMakanan.add(new Menu("Nasi Padang", 30000, "Makanan"));
        menuMakanan.add(new Menu("Ayam Penyet", 25000, "Makanan"));
        menuMakanan.add(new Menu("Sate Ayam", 35000, "Makanan"));
        menuMakanan.add(new Menu("Gado-Gado", 20000, "Makanan"));

        menuMinuman.add(new Menu("Teh Manis", 5000, "Minuman"));
        menuMinuman.add(new Menu("Kopi", 10000, "Minuman"));
        menuMinuman.add(new Menu("Jus Jeruk", 15000, "Minuman"));
        menuMinuman.add(new Menu("Air Mineral", 3000, "Minuman"));
    }

    private static void tampilkanMenu() {
        System.out.println("Daftar Menu Makanan:");
        for (Menu item : menuMakanan) {
            System.out.println(item.nama + " - Rp " + item.harga);
        }

        System.out.println("\nDaftar Menu Minuman:");
        for (Menu item : menuMinuman) {
            System.out.println(item.nama + " - Rp " + item.harga);
        }
    }

    private static void prosesPesanan(Scanner scanner) {
        List<String> pesanan = new ArrayList<>();
        double totalBiaya = 0;
        double totalPajak = 0;
        double diskon = 0;
        boolean tawaranMinumanDiterapkan = false;

        System.out.println("\nMasukkan pesanan (format: Nama Menu = Jumlah, ketik 'selesai' untuk mengakhiri):");
        while (true) {
            System.out.print("Pesanan: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("selesai")) {
                break;
            }
            pesanan.add(input);
        }

        for (String itemPesanan : pesanan) {
            String[] bagian = itemPesanan.split("=");
            String namaItem = bagian[0].trim();
            int jumlah = Integer.parseInt(bagian[1].trim());

            totalBiaya += hitungBiaya(namaItem, jumlah);
        }

        totalPajak = totalBiaya * TARIF_PAJAK;
        totalBiaya += totalPajak + BIAYA_PELAYANAN;

        if (totalBiaya > BATAS_DISKON) {
            diskon = totalBiaya * TARIF_DISKON;
        }

        if (totalBiaya > BATAS_TAWARAN_MINUMAN) {
            tawaranMinumanDiterapkan = true;
        }

        totalBiaya -= diskon;

        cetakStruk(totalBiaya, totalPajak, diskon, tawaranMinumanDiterapkan);
    }

    private static double hitungBiaya(String namaItem, int jumlah) {
        for (Menu item : menuMakanan) {
            if (item.nama.equalsIgnoreCase(namaItem)) {
                return item.harga * jumlah;
            }
        }
        for (Menu item : menuMinuman) {
            if (item.nama.equalsIgnoreCase(namaItem)) {
                return item.harga * jumlah;
            }
        }
        System.out.println("Menu " + namaItem + " tidak ditemukan.");
        return 0;
    }

    private static void cetakStruk(double totalBiaya, double totalPajak, double diskon, boolean tawaranMinumanDiterapkan) {
        System.out.println("\nStruk Pesanan:");
        System.out.println("Total Biaya: Rp " + totalBiaya);
        System.out.println("Biaya Pajak: Rp " + totalPajak);
        System.out.println("Biaya Pelayanan: Rp " + BIAYA_PELAYANAN);
        if (diskon > 0) {
            System.out.println("Diskon: Rp " + diskon);
        }
        if (tawaranMinumanDiterapkan) {
            System.out.println("Penawaran Beli Satu Gratis Satu untuk Minuman diterapkan.");
        }
    }

    private static void manajemenMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nManajemen Menu:");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // membersihkan newline

            switch (pilihan) {
                case 1:
                    tambahMenu(scanner);
                    break;
                case 2:
                    ubahMenu(scanner);
                    break;
                case 3:
                    hapusMenu(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void tambahMenu(Scanner scanner) {
        System.out.print("Masukkan nama menu: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga menu: ");
        double harga = scanner.nextDouble();
        scanner.nextLine(); // membersihkan newline
        System.out.print("Masukkan kategori (Makanan/Minuman): ");
        String kategori = scanner.nextLine();

        if (kategori.equalsIgnoreCase("Makanan")) {
            menuMakanan.add(new Menu(nama, harga, kategori));
        } else if (kategori.equalsIgnoreCase("Minuman")) {
            menuMinuman.add(new Menu(nama, harga, kategori));
        } else {
            System.out.println("Kategori tidak valid.");
        }
        System.out.println("Menu berhasil ditambahkan.");
    }

    private static void ubahMenu(Scanner scanner) {
        System.out.println("Daftar Menu Makanan:");
        for (int i = 0; i < menuMakanan.size(); i++) {
            System.out.println((i + 1) + ". " + menuMakanan.get(i).nama + " - Rp " + menuMakanan.get(i).harga);
        }

        System.out.println("Daftar Menu Minuman:");
        for (int i = 0; i < menuMinuman.size(); i++) {
            System.out.println((i + 1) + ". " + menuMinuman.get(i).nama + " - Rp " + menuMinuman.get(i).harga);
        }

        System.out.print("Pilih nomor menu yang ingin diubah: ");
        int nomor = scanner.nextInt() - 1;
        scanner.nextLine(); // membersihkan newline

        if (nomor < menuMakanan.size()) {
            System.out.print("Masukkan harga baru untuk " + menuMakanan.get(nomor).nama + ": ");
            double hargaBaru = scanner.nextDouble();
            menuMakanan.get(nomor).harga = hargaBaru;
            System.out.println("Menu berhasil diubah.");
        } else if (nomor - menuMakanan.size() < menuMinuman.size()) {
            nomor -= menuMakanan.size();
            System.out.print("Masukkan harga baru untuk " + menuMinuman.get(nomor).nama + ": ");
            double hargaBaru = scanner.nextDouble();
            menuMinuman.get(nomor).harga = hargaBaru;
            System.out.println("Menu berhasil diubah.");
        } else {
            System.out.println("Nomor menu tidak valid.");
        }
    }

    private static void hapusMenu(Scanner scanner) {
        System.out.println("Daftar Menu Makanan:");
        for (int i = 0; i < menuMakanan.size(); i++) {
            System.out.println((i + 1) + ". " + menuMakanan.get(i).nama);
        }

        System.out.println("Daftar Menu Minuman:");
        for (int i = 0; i < menuMinuman.size(); i++) {
            System.out.println((i + 1) + ". " + menuMinuman.get(i).nama);
        }

        System.out.print("Pilih nomor menu yang ingin dihapus: ");
        int nomor = scanner.nextInt() - 1;
        scanner.nextLine(); // membersihkan newline

        if (nomor < menuMakanan.size()) {
            System.out.print("Apakah Anda yakin ingin menghapus " + menuMakanan.get(nomor).nama + "? (Ya/Tidak): ");
            String konfirmasi = scanner.nextLine();
            if (konfirmasi.equalsIgnoreCase("Ya")) {
                menuMakanan.remove(nomor);
                System.out.println("Menu berhasil dihapus.");
            }
        } else if (nomor - menuMakanan.size() < menuMinuman.size()) {
            nomor -= menuMakanan.size();
            System.out.print("Apakah Anda yakin ingin menghapus " + menuMinuman.get(nomor).nama + "? (Ya/Tidak): ");
            String konfirmasi = scanner.nextLine();
            if (konfirmasi.equalsIgnoreCase("Ya")) {
                menuMinuman.remove(nomor);
                System.out.println("Menu berhasil dihapus.");
            }
        } else {
            System.out.println("Nomor menu tidak valid.");
        }
    }
}