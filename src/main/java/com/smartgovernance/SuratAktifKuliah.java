package com.smartgovernance;

public class SuratAktifKuliah extends PengajuanSurat {
    private int semester;

    public SuratAktifKuliah(String nomorPengajuan, String namaMahasiswa,
            String npm, String keperluan, int semester) {
        super(nomorPengajuan, namaMahasiswa, npm,
                "Surat Keterangan Aktif Kuliah",
                keperluan, "Menunggu Verifikasi");
        this.semester = semester;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public void tampilkanDetail() {
        super.tampilkanDetail();
        System.out.println("Semester        : " + semester);
        System.out.println("-----------------------------------");
    }
}
