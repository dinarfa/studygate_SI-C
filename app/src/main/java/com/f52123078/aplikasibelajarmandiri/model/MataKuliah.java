package com.f52123078.aplikasibelajarmandiri.model;

import com.google.firebase.firestore.DocumentId;

// Model POJO untuk data dari koleksi 'mata_kuliah'
public class MataKuliah {
    @DocumentId
    private String id; // Mengambil ID dokumen
    private String name;
    private String prodiId; // Field untuk relasi

    // --- BARU: Tambahkan field prodiName ---
    private String prodiName;

    // Konstruktor kosong wajib untuk Firestore
    public MataKuliah() {}

    // Konstruktor bisa disesuaikan jika perlu
    public MataKuliah(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getName() { return name; }
    public String getProdiId() { return prodiId; }
    // --- BARU: Getter untuk prodiName ---
    public String getProdiName() { return prodiName; }

    // --- Setter (Opsional, tapi bagus untuk konsistensi) ---
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setProdiId(String prodiId) { this.prodiId = prodiId; }
    // --- BARU: Setter untuk prodiName ---
    public void setProdiName(String prodiName) { this.prodiName = prodiName; }


    // Method untuk mendapatkan ID dokumen (sudah benar)
    public String getDocumentId() {
        return id;
    }

    // PENTING: Override toString() agar tampil di Spinner/AutoCompleteTextView
    @Override
    public String toString() {
        // Tampilkan nama saja di dropdown
        return name != null ? name : "";
    }
}

