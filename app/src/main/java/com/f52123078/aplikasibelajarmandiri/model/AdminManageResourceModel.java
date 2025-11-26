package com.f52123078.aplikasibelajarmandiri.model;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

// Model MVC untuk semua logika CRUD Resource
public class AdminManageResourceModel {

    // Interface untuk callback ke Controller
    public interface DataListener {
        void onProdiLoaded(List<Prodi> prodiList);
        void onMataKuliahLoaded(List<MataKuliah> mkList);
        void onResourcesLoaded(List<Resource> resourceList, List<String> documentIds); // Kirim ID juga
        void onWriteSuccess(String message);
        void onError(String error);
    }

    private final FirebaseFirestore db;
    private final CollectionReference prodiCol;
    private final CollectionReference mkCol;
    private final CollectionReference resourcesCol;

    // Untuk listener realtime
    private ListenerRegistration resourceListenerRegistration;

    public AdminManageResourceModel() {
        db = FirebaseFirestore.getInstance();
        prodiCol = db.collection("prodi");
        mkCol = db.collection("mata_kuliah");
        resourcesCol = db.collection("resources");
    }

    // --- FUNGSI LOAD DATA ---

    public void loadProdi(DataListener listener) {
        prodiCol.orderBy("name").get().addOnSuccessListener(snapshot -> {
            listener.onProdiLoaded(snapshot.toObjects(Prodi.class));
        }).addOnFailureListener(e -> listener.onError("Gagal memuat Prodi: " + e.getMessage()));
    }

    public void loadMataKuliah(String prodiId, DataListener listener) {
        if (prodiId == null || prodiId.isEmpty()) {
            listener.onError("Prodi ID tidak valid");
            return;
        }

        // Query 'mata_kuliah' berdasarkan 'prodiId'
        mkCol.whereEqualTo("prodiId", prodiId).orderBy("name").get().addOnSuccessListener(snapshot -> {
            listener.onMataKuliahLoaded(snapshot.toObjects(MataKuliah.class));
        }).addOnFailureListener(e -> listener.onError("Gagal memuat Mata Kuliah: " + e.getMessage()));
    }

    // Menggunakan onSnapshot untuk data realtime
    public void loadResourcesRealtime(DataListener listener) {
        // Hentikan listener lama jika ada
        if (resourceListenerRegistration != null) {
            resourceListenerRegistration.remove();
        }

        // Buat listener baru
        resourceListenerRegistration = resourcesCol.orderBy("title")
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) {
                        listener.onError("Gagal memuat resource: " + e.getMessage());
                        return;
                    }

                    if (snapshot != null && !snapshot.isEmpty()) {
                        List<Resource> resources = snapshot.toObjects(Resource.class);
                        // Kita perlu ID dokumen untuk edit/delete
                        List<String> ids = new java.util.ArrayList<>();
                        for (com.google.firebase.firestore.DocumentSnapshot doc : snapshot.getDocuments()) {
                            ids.add(doc.getId());
                        }
                        listener.onResourcesLoaded(resources, ids);
                    } else {
                        listener.onResourcesLoaded(new java.util.ArrayList<>(), new java.util.ArrayList<>());
                    }
                });
    }

    // Panggil ini saat Activity di-destroy untuk menghindari memory leak
    public void detachResourceListener() {
        if (resourceListenerRegistration != null) {
            resourceListenerRegistration.remove();
        }
    }

    // --- FUNGSI TULIS DATA (CRUD) ---

    public void addResource(Resource resource, DataListener listener) {
        resourcesCol.add(resource)
                .addOnSuccessListener(docRef -> listener.onWriteSuccess("Resource berhasil ditambahkan!"))
                .addOnFailureListener(e -> listener.onError("Gagal menambah: " + e.getMessage()));
    }

    public void updateResource(String documentId, Resource resource, DataListener listener) {
        DocumentReference docRef = resourcesCol.document(documentId);
        docRef.set(resource) // .set() akan menimpa seluruh dokumen
                .addOnSuccessListener(aVoid -> listener.onWriteSuccess("Resource berhasil diperbarui!"))
                .addOnFailureListener(e -> listener.onError("Gagal memperbarui: " + e.getMessage()));
    }

    public void deleteResource(String documentId, DataListener listener) {
        resourcesCol.document(documentId).delete()
                .addOnSuccessListener(aVoid -> listener.onWriteSuccess("Resource berhasil dihapus!"))
                .addOnFailureListener(e -> listener.onError("Gagal menghapus: " + e.getMessage()));
    }
}

