package com.smartgovernance;

public class SuratPengantarMagang extends PengajuanSurat {
    private String namaInstansi;

    public SuratPengantarMagang(String nomorPengajuan,
            String namaMahasiswa, String npm,
            String keperluan, String namaInstansi) {
        super(nomorPengajuan, namaMahasiswa, npm,
                "Surat Pengantar Magang",
                keperluan, "Menunggu Verifikasi");
        this.namaInstansi = namaInstansi;
    }

    public String getNamaInstansi() {
        return namaInstansi;
    }

    public void setNamaInstansi(String namaInstansi) {
        this.namaInstansi = namaInstansi;
    }

    @Override
    public void tampilkanDetail() {
        super.tampilkanDetail();
        System.out.println("Instansi Tujuan : " + namaInstansi);
        System.out.println("-----------------------------------");
    }
}
