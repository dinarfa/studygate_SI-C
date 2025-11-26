package com.f52123078.aplikasibelajarmandiri.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue; // Import FieldValue
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions; // Import SetOptions

import java.util.HashMap;
import java.util.Map;

public class UserActivityModel {

    private static final String TAG = "UserActivityModel";
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private FirebaseUser currentUser;

    public UserActivityModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    /**
     * Menyimpan informasi resource terakhir yang diakses user ke Firestore.
     * Menggunakan Map untuk struktur data lastAccessedResource.
     * Menggunakan FieldValue.serverTimestamp() untuk timestamp otomatis.
     */

    public void saveLastAccessedResource(String resourceId, String resourceTitle, String resourceDesc, String resourceUrl) {
        if (currentUser == null) {
            Log.w(TAG, "Tidak ada user yang login, tidak bisa menyimpan last accessed.");
            return;
        }

        DocumentReference userRef = db.collection("users").document(currentUser.getUid());

        // Buat Map untuk data lastAccessedResource
        Map<String, Object> lastAccessedData = new HashMap<>();
        lastAccessedData.put("id", resourceId);
        lastAccessedData.put("title", resourceTitle);
        lastAccessedData.put("desc", resourceDesc != null ? resourceDesc : "");
        lastAccessedData.put("timestamp", FieldValue.serverTimestamp());
        lastAccessedData.put("url", resourceUrl != null ? resourceUrl : ""); // <-- PERBAIKAN 2: Simpan URL

        // Buat Map utama untuk update dokumen user
        Map<String, Object> userData = new HashMap<>();
        userData.put("lastAccessedResource", lastAccessedData);

        // Update dokumen user, merge agar tidak menimpa field lain
        userRef.set(userData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Last accessed resource berhasil disimpan."))
                .addOnFailureListener(e -> Log.e(TAG, "Gagal menyimpan last accessed resource: ", e));
    }
    public void saveLastAccessedResource(String resourceId, String resourceTitle, String resourceDesc) {
        if (currentUser == null) {
            Log.w(TAG, "Tidak ada user yang login, tidak bisa menyimpan last accessed.");
            return;
        }

        DocumentReference userRef = db.collection("users").document(currentUser.getUid());

        // Buat Map untuk data lastAccessedResource
        Map<String, Object> lastAccessedData = new HashMap<>();
        lastAccessedData.put("id", resourceId);
        lastAccessedData.put("title", resourceTitle);
        lastAccessedData.put("desc", resourceDesc != null ? resourceDesc : ""); // Pastikan desc tidak null
        lastAccessedData.put("timestamp", FieldValue.serverTimestamp()); // Gunakan timestamp server

        // Buat Map utama untuk update dokumen user
        Map<String, Object> userData = new HashMap<>();
        userData.put("lastAccessedResource", lastAccessedData);

        // Update dokumen user, merge agar tidak menimpa field lain
        userRef.set(userData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Last accessed resource berhasil disimpan."))
                .addOnFailureListener(e -> Log.e(TAG, "Gagal menyimpan last accessed resource: ", e));
    }
}
