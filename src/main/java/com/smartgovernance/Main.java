package com.smartgovernance;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private final ObservableList<PengajuanSurat> daftarPengajuan
            = FXCollections.observableArrayList();

    private TextField namaField;
    private TextField npmField;
    private TextField detailField;
    private TextArea keperluanArea;
    private ComboBox<String> jenisSuratCombo;
    private ComboBox<String> statusCombo;
    private TableView<PengajuanSurat> tabelPengajuan;

    private Label totalValue;
    private Label menungguValue;
    private Label disetujuiValue;

    private int nomorPengajuanBerikutnya = 1;

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        VBox halaman = new VBox(15);
        halaman.getChildren().addAll(
                buatRingkasan(),
                buatAreaUtama()
        );

        root.setTop(buatHeader());
        root.setCenter(halaman);

        BorderPane.setMargin(halaman, new Insets(15, 0, 0, 0));

        Scene scene = new Scene(root, 1150, 750);

        scene.getStylesheets().add(
                getClass().getResource("/com/smartgovernance/style.css").toExternalForm()
        );

        stage.setTitle("Smart Governance");
        stage.setMinWidth(950);
        stage.setMinHeight(650);
        stage.setScene(scene);
        stage.show();
    }

    private HBox buatHeader() {

        Label judul = new Label("Smart Governance");
        judul.getStyleClass().add("app-title");

        Label deskripsi = new Label(
                "Sistem Pengajuan dan Pelacakan Surat Mahasiswa"
        );

        VBox header = new VBox(5, judul, deskripsi);

        HBox hasil = new HBox(header);
        hasil.setAlignment(Pos.CENTER_LEFT);

        return hasil;
    }

    private HBox buatRingkasan() {

        totalValue = new Label("0");
        menungguValue = new Label("0");
        disetujuiValue = new Label("0");

        VBox total = buatKartuRingkasan("Total Pengajuan", totalValue);
        VBox menunggu = buatKartuRingkasan("Menunggu Verifikasi", menungguValue);
        VBox disetujui = buatKartuRingkasan("Disetujui", disetujuiValue);

        HBox ringkasan = new HBox(15, total, menunggu, disetujui);

        return ringkasan;
    }

    private VBox buatKartuRingkasan(String judul, Label nilai) {

        Label label = new Label(judul);

        VBox kartu = new VBox(5, label, nilai);
        kartu.getStyleClass().add("stat-card");

        HBox.setHgrow(kartu, Priority.ALWAYS);

        return kartu;
    }

    private HBox buatAreaUtama() {

        VBox form = buatKartuForm();
        VBox tabel = buatKartuTabel();

        HBox area = new HBox(15, form, tabel);

        area.setAlignment(Pos.TOP_LEFT);

        HBox.setHgrow(tabel, Priority.ALWAYS);

        return area;
    }

    private VBox buatKartuForm() {

        Label judul = new Label("Form Pengajuan Surat");

        namaField = new TextField();
        namaField.setPromptText("Nama mahasiswa");

        npmField = new TextField();
        npmField.setPromptText("NPM");

        jenisSuratCombo = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Surat Keterangan Aktif Kuliah",
                        "Surat Pengantar Magang"
                )
        );

        jenisSuratCombo.setMaxWidth(Double.MAX_VALUE);
        jenisSuratCombo.setValue("Surat Keterangan Aktif Kuliah");

        detailField = new TextField();

        keperluanArea = new TextArea();
        keperluanArea.setPromptText("Keperluan pengajuan");
        keperluanArea.setWrapText(true);
        keperluanArea.setPrefRowCount(3);
        keperluanArea.setMaxHeight(90);

        Label detailLabel = new Label("Semester");

        VBox form = new VBox(10,
                buatInput("Nama Mahasiswa", namaField),
                buatInput("NPM", npmField),
                buatInput("Jenis Surat", jenisSuratCombo),
                buatInput("Semester", detailField),
                buatInput("Keperluan", keperluanArea)
        );

        jenisSuratCombo.setOnAction(event ->
                perbaruiInputTambahan(detailLabel)
        );

        perbaruiInputTambahan(detailLabel);

        Button ajukanButton = new Button("Kirim Pengajuan");
        ajukanButton.setMaxWidth(Double.MAX_VALUE);
        ajukanButton.setOnAction(event -> prosesPengajuan());

        Button resetButton = new Button("Reset");
        resetButton.setMaxWidth(Double.MAX_VALUE);
        resetButton.setOnAction(event -> resetForm());

        HBox tombol = new HBox(10, ajukanButton, resetButton);

        VBox kartu = new VBox(12,
                judul,
                form,
                tombol
        );

        kartu.getStyleClass().add("content-card");
        kartu.setPrefWidth(300);
        kartu.setMinWidth(280);

        return kartu;
    }

    private VBox buatInput(String judul, javafx.scene.Node input) {

        Label label = new Label(judul);

        if (input instanceof Region region) {
            region.setMaxWidth(Double.MAX_VALUE);
        }

        return new VBox(5, label, input);
    }

    private void perbaruiInputTambahan(Label detailLabel) {

        if (jenisSuratCombo.getValue().equals(
                "Surat Keterangan Aktif Kuliah")) {

            detailLabel.setText("Semester");
            detailField.setPromptText("Masukkan semester (1-14)");

        } else {

            detailLabel.setText("Nama Instansi Tujuan");
            detailField.setPromptText("Nama instansi");
        }

        detailField.clear();
    }

    private VBox buatKartuTabel() {

        Label judul = new Label("Pelacakan Pengajuan");

        tabelPengajuan = new TableView<>(daftarPengajuan);
        tabelPengajuan.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        tabelPengajuan.setPlaceholder(
                new Label("Belum ada pengajuan.")
        );

        tabelPengajuan.setPrefHeight(430);

        TableColumn<PengajuanSurat, String> nomorCol =
                new TableColumn<>("Nomor");

        nomorCol.setCellValueFactory(
                new PropertyValueFactory<>("nomorPengajuan")
        );

        TableColumn<PengajuanSurat, String> namaCol =
                new TableColumn<>("Nama");

        namaCol.setCellValueFactory(
                new PropertyValueFactory<>("namaMahasiswa")
        );

        TableColumn<PengajuanSurat, String> npmCol =
                new TableColumn<>("NPM");

        npmCol.setCellValueFactory(
                new PropertyValueFactory<>("npm")
        );

        TableColumn<PengajuanSurat, String> jenisCol =
                new TableColumn<>("Jenis Surat");

        jenisCol.setCellValueFactory(
                new PropertyValueFactory<>("jenisSurat")
        );

        TableColumn<PengajuanSurat, String> detailCol =
                new TableColumn<>("Detail");

        detailCol.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        ambilDetailTambahan(cellData.getValue())
                )
        );

        TableColumn<PengajuanSurat, String> keperluanCol =
                new TableColumn<>("Keperluan");

        keperluanCol.setCellValueFactory(
                new PropertyValueFactory<>("keperluan")
        );

        TableColumn<PengajuanSurat, String> statusCol =
                new TableColumn<>("Status");

        statusCol.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        tabelPengajuan.getColumns().addAll(
                nomorCol,
                namaCol,
                npmCol,
                jenisCol,
                detailCol,
                keperluanCol,
                statusCol
        );

        statusCombo = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Menunggu Verifikasi",
                        "Diproses",
                        "Disetujui",
                        "Ditolak"
                )
        );

        statusCombo.setPromptText("Pilih status");

        Button updateButton = new Button("Perbarui Status");
        updateButton.setOnAction(event -> perbaruiStatus());

        Button hapusButton = new Button("Hapus Pengajuan");
        hapusButton.setOnAction(event -> hapusPengajuan());

        tabelPengajuan.getSelectionModel().selectedItemProperty().addListener(
                (observable, dataLama, dataBaru) -> {

                    if (dataBaru != null) {
                        statusCombo.setValue(dataBaru.getStatus());
                    }
                }
        );

        HBox kontrolStatus = new HBox(
                10,
                new Label("Status:"),
                statusCombo,
                updateButton,
                hapusButton
        );

        kontrolStatus.setAlignment(Pos.CENTER_LEFT);

        VBox kartu = new VBox(
                12,
                judul,
                tabelPengajuan,
                kontrolStatus
        );

        kartu.getStyleClass().add("content-card");

        VBox.setVgrow(tabelPengajuan, Priority.ALWAYS);

        return kartu;
    }

    private String ambilDetailTambahan(PengajuanSurat surat) {

        if (surat instanceof SuratAktifKuliah aktif) {
            return "Semester " + aktif.getSemester();
        }

        if (surat instanceof SuratPengantarMagang magang) {
            return magang.getNamaInstansi();
        }

        return "-";
    }

    private void prosesPengajuan() {

        String nama = namaField.getText().trim();
        String npm = npmField.getText().trim();
        String keperluan = keperluanArea.getText().trim();
        String jenis = jenisSuratCombo.getValue();
        String detail = detailField.getText().trim();

        if (nama.isEmpty() || npm.isEmpty()
                || keperluan.isEmpty() || detail.isEmpty()) {

            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Data Belum Lengkap",
                    "Semua kolom wajib diisi.",
                    "Periksa kembali form pengajuan."
            );

            return;
        }

        if (!npm.matches("[0-9]+")) {

            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "NPM Tidak Valid",
                    "NPM harus berupa angka.",
                    "Masukkan NPM tanpa huruf."
            );

            npmField.requestFocus();
            return;
        }

        String nomor = String.format(
                "SURAT-%03d",
                nomorPengajuanBerikutnya
        );

        PengajuanSurat suratBaru;

        if (jenis.equals("Surat Keterangan Aktif Kuliah")) {

            int semester;

            try {
                semester = Integer.parseInt(detail);
            } catch (NumberFormatException exception) {

                tampilkanAlert(
                        Alert.AlertType.WARNING,
                        "Semester Tidak Valid",
                        "Semester harus berupa angka.",
                        "Masukkan semester dari 1 sampai 14."
                );

                detailField.requestFocus();
                return;
            }

            if (semester < 1 || semester > 14) {

                tampilkanAlert(
                        Alert.AlertType.WARNING,
                        "Semester Tidak Valid",
                        "Semester harus 1 sampai 14.",
                        "Periksa kembali semester."
                );

                detailField.requestFocus();
                return;
            }

            suratBaru = new SuratAktifKuliah(
                    nomor,
                    nama,
                    npm,
                    keperluan,
                    semester
            );

        } else {

            suratBaru = new SuratPengantarMagang(
                    nomor,
                    nama,
                    npm,
                    keperluan,
                    detail
            );
        }

        daftarPengajuan.add(suratBaru);
        nomorPengajuanBerikutnya++;

        perbaruiRingkasan();
        resetForm();

        tampilkanAlert(
                Alert.AlertType.INFORMATION,
                "Berhasil",
                "Pengajuan berhasil dikirim.",
                "Nomor pengajuan: " + nomor
                        + "\nStatus: Menunggu Verifikasi"
        );
    }

    private void perbaruiStatus() {

        PengajuanSurat terpilih =
                tabelPengajuan.getSelectionModel().getSelectedItem();

        if (terpilih == null) {

            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Data Belum Dipilih",
                    "Pilih salah satu pengajuan.",
                    "Klik baris pada tabel terlebih dahulu."
            );

            return;
        }

        String statusBaru = statusCombo.getValue();

        if (statusBaru == null) {

            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Status Belum Dipilih",
                    "Pilih status terlebih dahulu.",
                    "Pilih status yang tersedia."
            );

            return;
        }

        terpilih.setStatus(statusBaru);

        tabelPengajuan.refresh();
        perbaruiRingkasan();

        tampilkanAlert(
                Alert.AlertType.INFORMATION,
                "Berhasil",
                "Status berhasil diperbarui.",
                terpilih.getNomorPengajuan()
                        + " sekarang berstatus: " + statusBaru
        );
    }

    private void hapusPengajuan() {

        PengajuanSurat terpilih =
                tabelPengajuan.getSelectionModel().getSelectedItem();

        if (terpilih == null) {

            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Data Belum Dipilih",
                    "Pilih pengajuan yang ingin dihapus.",
                    "Klik salah satu baris pada tabel."
            );

            return;
        }

        Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);

        konfirmasi.setTitle("Hapus Pengajuan");
        konfirmasi.setHeaderText(
                "Yakin ingin menghapus pengajuan ini?"
        );

        konfirmasi.setContentText(
                "Nomor pengajuan: " + terpilih.getNomorPengajuan()
        );

        konfirmasi.initOwner(
                tabelPengajuan.getScene().getWindow()
        );

        if (konfirmasi.showAndWait().orElse(ButtonType.CANCEL)
                == ButtonType.OK) {

            daftarPengajuan.remove(terpilih);

            statusCombo.setValue(null);

            perbaruiRingkasan();

            tampilkanAlert(
                    Alert.AlertType.INFORMATION,
                    "Berhasil",
                    "Pengajuan berhasil dihapus.",
                    "Data sudah dihapus dari tabel."
            );
        }
    }

    private void resetForm() {

        namaField.clear();
        npmField.clear();
        keperluanArea.clear();

        jenisSuratCombo.setValue(
                "Surat Keterangan Aktif Kuliah"
        );

        detailField.clear();
        namaField.requestFocus();
    }

    private void perbaruiRingkasan() {

        long menunggu = daftarPengajuan.stream()
                .filter(surat ->
                        surat.getStatus().equals("Menunggu Verifikasi"))
                .count();

        long disetujui = daftarPengajuan.stream()
                .filter(surat ->
                        surat.getStatus().equals("Disetujui"))
                .count();

        totalValue.setText(
                String.valueOf(daftarPengajuan.size())
        );

        menungguValue.setText(
                String.valueOf(menunggu)
        );

        disetujuiValue.setText(
                String.valueOf(disetujui)
        );
    }

    private void tampilkanAlert(
            Alert.AlertType tipe,
            String judul,
            String header,
            String pesan) {

        Alert alert = new Alert(tipe);

        alert.setTitle(judul);
        alert.setHeaderText(header);
        alert.setContentText(pesan);

        if (tabelPengajuan != null && tabelPengajuan.getScene() != null) {
            alert.initOwner(
                    tabelPengajuan.getScene().getWindow()
            );
        }

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
