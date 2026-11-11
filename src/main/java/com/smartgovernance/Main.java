package com.smartgovernance;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<PengajuanSurat> daftarPengajuan = new ArrayList<>();

    public static void main(String[] args) {
        int pilihan;

        do {
            tampilkanMenu();
            pilihan = bacaAngka("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    ajukanSurat();
                    break;
                case 2:
                    lihatPengajuan();
                    break;
                case 3:
                    ubahStatus();
                    break;
                case 4:
                    System.out.println("Terima kasih telah menggunakan Smart Governance.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia. Silakan pilih 1-4.");
            }
        } while (pilihan != 4);
    }

    private static void tampilkanMenu() {
        System.out.println("\n========================================");
        System.out.println("             SMART GOVERNANCE");
        System.out.println(" SISTEM PENGAJUAN SURAT MAHASISWA");
        System.out.println("========================================");
        System.out.println("1. Ajukan Surat");
        System.out.println("2. Lihat Pengajuan");
        System.out.println("3. Ubah Status Pengajuan");
        System.out.println("4. Keluar");
    }

    private static int bacaAngka(String pesan) {
        while (true) {
            System.out.print(pesan);
            String nilai = input.nextLine().trim();

            try {
                return Integer.parseInt(nilai);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
            }
        }
    }

    private static String bacaTeks(String pesan) {
        while (true) {
            System.out.print(pesan);
            String nilai = input.nextLine().trim();

            if (!nilai.isEmpty()) {
                return nilai;
            }

            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static void ajukanSurat() {
        System.out.println("\n--- PENGAJUAN SURAT ---");
        System.out.println("1. Surat Keterangan Aktif Kuliah");
        System.out.println("2. Surat Pengantar Magang");

        int jenis = bacaAngka("Pilih jenis surat: ");
        if (jenis != 1 && jenis != 2) {
            System.out.println("Jenis surat tidak tersedia.");
            return;
        }

        String nama = bacaTeks("Nama Mahasiswa : ");
        String npm = bacaTeks("NPM            : ");
        String keperluan = bacaTeks("Keperluan      : ");
        String nomor = String.format("SURAT-%03d", daftarPengajuan.size() + 1);

        if (jenis == 1) {
            int semester;
            do {
                semester = bacaAngka("Semester       : ");
                if (semester < 1 || semester > 14) {
                    System.out.println("Semester harus antara 1 sampai 14.");
                }
            } while (semester < 1 || semester > 14);

            SuratAktifKuliah surat = new SuratAktifKuliah(
                    nomor, nama, npm, keperluan, semester);
            daftarPengajuan.add(surat);
        } else {
            String instansi = bacaTeks("Nama Instansi  : ");
            SuratPengantarMagang surat = new SuratPengantarMagang(
                    nomor, nama, npm, keperluan, instansi);
            daftarPengajuan.add(surat);
        }

        System.out.println("\nPengajuan berhasil dibuat.");
        System.out.println("Nomor Pengajuan: " + nomor);
    }

    private static void lihatPengajuan() {
        System.out.println("\n--- DAFTAR PENGAJUAN ---");

        if (daftarPengajuan.isEmpty()) {
            System.out.println("Belum ada pengajuan surat.");
            return;
        }

        for (PengajuanSurat surat : daftarPengajuan) {
            surat.tampilkanDetail();
        }
    }

    private static void ubahStatus() {
        if (daftarPengajuan.isEmpty()) {
            System.out.println("Belum ada pengajuan surat.");
            return;
        }

        System.out.println("\n--- PILIH PENGAJUAN ---");
        for (int i = 0; i < daftarPengajuan.size(); i++) {
            PengajuanSurat surat = daftarPengajuan.get(i);
            System.out.println((i + 1) + ". " + surat.getNomorPengajuan()
                    + " - " + surat.getNamaMahasiswa()
                    + " (" + surat.getStatus() + ")");
        }

        int nomor = bacaAngka("Pilih nomor pengajuan: ");
        if (nomor < 1 || nomor > daftarPengajuan.size()) {
            System.out.println("Nomor pengajuan tidak ditemukan.");
            return;
        }

        System.out.println("\nPilih Status Baru:");
        System.out.println("1. Menunggu Verifikasi");
        System.out.println("2. Diproses");
        System.out.println("3. Disetujui");
        System.out.println("4. Ditolak");

        int pilihanStatus = bacaAngka("Pilih status: ");
        String statusBaru;

        switch (pilihanStatus) {
            case 1:
                statusBaru = "Menunggu Verifikasi";
                break;
            case 2:
                statusBaru = "Diproses";
                break;
            case 3:
                statusBaru = "Disetujui";
                break;
            case 4:
                statusBaru = "Ditolak";
                break;
            default:
                System.out.println("Pilihan status tidak tersedia.");
                return;
        }

        daftarPengajuan.get(nomor - 1).setStatus(statusBaru);
        System.out.println("Status pengajuan berhasil diperbarui menjadi: " + statusBaru);
    }
}
