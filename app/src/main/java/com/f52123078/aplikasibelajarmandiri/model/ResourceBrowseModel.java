package com.f52123078.aplikasibelajarmandiri.model;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot; // <-- TAMBAHKAN IMPORT
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList; // <-- TAMBAHKAN IMPORT
import java.util.List;

public class ResourceBrowseModel {

    // --- PERBAIKAN 1: Ubah Interface ---
    public interface BrowseDataListener {
        void onProdiLoaded(List<Prodi> prodiList);
        void onMataKuliahLoaded(List<MataKuliah> mkList);
        // Ubah method ini untuk menyertakan documentIds
        void onResourcesFiltered(List<Resource> resourceList, List<String> documentIds);
        void onError(String error);
    }

    private final FirebaseFirestore db;
    private final CollectionReference prodiCol;
    private final CollectionReference mkCol;
    private final CollectionReference resourcesCol;

    public ResourceBrowseModel() {
        db = FirebaseFirestore.getInstance();
        prodiCol = db.collection("prodi");
        mkCol = db.collection("mata_kuliah");
        resourcesCol = db.collection("resources");
    }

    // --- FUNGSI LOAD DROPDOWN (Tidak berubah) ---
    // ... (loadProdi dan loadMataKuliah biarkan saja) ...
    public void loadProdi(BrowseDataListener listener) {
        prodiCol.orderBy("name").get().addOnSuccessListener(snapshot -> {
            listener.onProdiLoaded(snapshot.toObjects(Prodi.class));
        }).addOnFailureListener(e -> listener.onError("Gagal memuat Prodi: " + e.getMessage()));
    }

    public void loadMataKuliah(String prodiId, BrowseDataListener listener) {
        if (prodiId == null || prodiId.isEmpty()) {
            listener.onError("Prodi ID tidak valid");
            return;
        }
        mkCol.whereEqualTo("prodiId", prodiId).orderBy("name").get().addOnSuccessListener(snapshot -> {
            listener.onMataKuliahLoaded(snapshot.toObjects(MataKuliah.class));
        }).addOnFailureListener(e -> listener.onError("Gagal memuat Mata Kuliah: " + e.getMessage()));
    }


    // --- FUNGSI UTAMA: FILTER ---

    /**
     * Mengambil resource berdasarkan filter.
     * Jika prodiId null, ambil semua.
     * Jika mkId null, ambil berdasarkan prodiId saja.
     */
    public void loadFilteredResources(String prodiId, String mkId, BrowseDataListener listener) {

        // Mulai dengan query dasar
        Query query = resourcesCol;

        // Terapkan filter
        if (prodiId != null && !prodiId.isEmpty()) {
            query = query.whereEqualTo("prodiId", prodiId);
        }
        if (mkId != null && !mkId.isEmpty()) {
            query = query.whereEqualTo("mkId", mkId);
        }

        // --- PERBAIKAN 2: Ubah onSuccessListener untuk mengambil Doc ID ---
        query.orderBy("title").get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Resource> resources = new ArrayList<>();
                    List<String> docIds = new ArrayList<>(); // <-- Buat list untuk IDs

                    // Loop manual untuk mendapatkan ID
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Resource res = doc.toObject(Resource.class);
                        if (res != null) {
                            resources.add(res);
                            docIds.add(doc.getId()); // <-- Simpan ID dokumen
                        }
                    }
                    listener.onResourcesFiltered(resources, docIds); // <-- Kirim kedua list
                })
                .addOnFailureListener(e -> {
                    listener.onError("Gagal memfilter resource: " + e.getMessage());
                    // Opsional: kirim list kosong agar UI ter-update
                    // listener.onResourcesFiltered(new ArrayList<>(), new ArrayList<>());
                });
    }
}