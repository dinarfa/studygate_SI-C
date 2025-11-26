package com.f52123078.aplikasibelajarmandiri.model;

import com.google.firebase.firestore.DocumentId;

// Model POJO untuk data dari koleksi 'prodi'
public class Prodi {
    @DocumentId
    private String id; // Mengambil ID dokumen
    private String name;

    // Konstruktor kosong wajib untuk Firestore
    public Prodi() {}

    public Prodi(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; } // Getter untuk ID
    public String getName() { return name; }

    //  Override toString() agar tampil di Spinner/AutoCompleteTextView
    @Override
    public String toString() {
        return name;
    }

    // Method ini sekarang mengembalikan 'id' yang didapat dari @DocumentId
    public String getDocumentId() {
        return id;
    }
}
