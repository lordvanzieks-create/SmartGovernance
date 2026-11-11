package com.smartgovernance;

public class PengajuanSurat {
    private String nomorPengajuan;
    private String namaMahasiswa;
    private String npm;
    private String jenisSurat;
    private String keperluan;
    private String status;

    public PengajuanSurat(String nomorPengajuan, String namaMahasiswa,
            String npm, String jenisSurat, String keperluan, String status) {
        this.nomorPengajuan = nomorPengajuan;
        this.namaMahasiswa = namaMahasiswa;
        this.npm = npm;
        this.jenisSurat = jenisSurat;
        this.keperluan = keperluan;
        this.status = status;
    }

    public String getNomorPengajuan() {
        return nomorPengajuan;
    }

    public void setNomorPengajuan(String nomorPengajuan) {
        this.nomorPengajuan = nomorPengajuan;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public void setNamaMahasiswa(String namaMahasiswa) {
        this.namaMahasiswa = namaMahasiswa;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getJenisSurat() {
        return jenisSurat;
    }

    public void setJenisSurat(String jenisSurat) {
        this.jenisSurat = jenisSurat;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void tampilkanDetail() {
        System.out.println("Nomor Pengajuan : " + nomorPengajuan);
        System.out.println("Nama Mahasiswa  : " + namaMahasiswa);
        System.out.println("NPM             : " + npm);
        System.out.println("Jenis Surat     : " + jenisSurat);
        System.out.println("Keperluan       : " + keperluan);
        System.out.println("Status          : " + status);
    }
}
