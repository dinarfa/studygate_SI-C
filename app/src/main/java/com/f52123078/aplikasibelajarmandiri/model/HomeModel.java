package com.f52123078.aplikasibelajarmandiri.model; // Sesuaikan package

import android.util.Log;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeModel {

    // --- Interface dengan EMPAT method callback ---
    public interface HomeDataListener {
        void onUserDataLoaded(String userName);
        void onRecentResourcesLoaded(List<Resource> resources, List<String> documentIds); // 2 Parameter
        void onLastAccessedLoaded(@Nullable Map<String, Object> lastAccessedData);
        void onDataLoadError(String error);
    }

    private static final String TAG = "HomeModel";
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private FirebaseUser currentUser;

    public HomeModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    /**
     * Memuat semua data yang dibutuhkan untuk Home dan Account fragment.
     */
    public void loadHomeData(final HomeDataListener listener) {
        if (currentUser == null) {
            Log.w(TAG, "loadHomeData: currentUser is null.");
            listener.onDataLoadError("Tidak ada user yang login.");
            return;
        }
        Log.d(TAG, "loadHomeData: Loading data for user: " + currentUser.getUid());
        loadUserName(currentUser.getUid(), listener);
        loadRecentResources(listener);
        loadLastAccessedResource(currentUser.getUid(), listener);
    }

    /**
     * Memuat hanya nama user (dipanggil oleh AccountFragment).
     */
    public void loadUserName(String uid, final HomeDataListener listener) {
        if (uid == null || uid.isEmpty()) {
            Log.w(TAG, "loadUserName: Invalid UID provided.");
            listener.onDataLoadError("User ID tidak valid.");
            return;
        }
        Log.d(TAG, "loadUserName: Fetching username for UID: " + uid);
        DocumentReference userRef = db.collection("users").document(uid);
        userRef.get().addOnSuccessListener(document -> {
            if (document.exists()) {
                String name = document.getString("name");
                Log.d(TAG, "loadUserName: User document found, name: " + (name != null ? name : "null"));
                listener.onUserDataLoaded(name != null ? name : "Pengguna");
            } else {
                Log.w(TAG, "loadUserName: User document not found for UID: " + uid);
                // Tetap kirim callback agar UI tahu proses selesai
                listener.onUserDataLoaded("Pengguna"); // Default name
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "loadUserName: Gagal mengambil data user: ", e);
            listener.onDataLoadError("Gagal mengambil data user: " + e.getMessage());
        });
    }

    /**
     * Memuat resource terbaru (max 5) untuk HomeFragment.
     */
    private void loadRecentResources(final HomeDataListener listener) {
        Log.d(TAG, "loadRecentResources: Fetching recent resources...");
        db.collection("resources")
                // PENTING: Jika menggunakan orderBy, pastikan Index sudah dibuat di Firestore
                // Jika belum, hapus (komen) baris orderBy ini untuk sementara
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Resource> resourceList = new ArrayList<>();
                    List<String> docIds = new ArrayList<>();
                    if (!querySnapshot.isEmpty()) {
                        Log.d(TAG, "loadRecentResources: Found " + querySnapshot.size() + " documents.");
                        try {
                            for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                                Resource res = doc.toObject(Resource.class);
                                if (res != null) {
                                    resourceList.add(res);
                                    docIds.add(doc.getId());
                                } else {
                                    Log.w(TAG, "loadRecentResources: Failed to convert document " + doc.getId() + " to Resource object.");
                                }
                            }
                            Log.d(TAG, "loadRecentResources: Successfully loaded " + resourceList.size() + " resources.");
                        } catch (Exception e) {
                            Log.e(TAG, "loadRecentResources: Error converting Firestore data to Resource objects: ", e);
                            listener.onDataLoadError("Gagal memproses data resource terbaru: " + e.getMessage());
                            // Kirim list kosong jika ada error konversi
                            listener.onRecentResourcesLoaded(new ArrayList<>(), new ArrayList<>());
                            return; // Hentikan eksekusi jika konversi gagal
                        }
                    } else {
                        Log.d(TAG, "loadRecentResources: No recent resources found.");
                    }
                    // Panggil callback DENGAN DUA parameter
                    listener.onRecentResourcesLoaded(resourceList, docIds);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "loadRecentResources: Gagal mengambil resources: ", e);
                    String errorMsg = "Gagal mengambil resources: " + e.getMessage();
                    // Beri petunjuk jika error karena index
                    if (e.getMessage() != null && e.getMessage().contains("FAILED_PRECONDITION")) {
                        errorMsg = "Gagal mengambil resources terbaru: Index Firestore dibutuhkan. Cek Logcat (level Warning/Error) untuk link pembuatan index.";
                        Log.w(TAG, errorMsg); // Log sebagai warning juga
                    }
                    listener.onDataLoadError(errorMsg);
                    // Kirim list kosong saat error
                    listener.onRecentResourcesLoaded(new ArrayList<>(), new ArrayList<>());
                });
    }

    /**
     * Memuat data resource terakhir diakses untuk HomeFragment.
     */
    private void loadLastAccessedResource(String uid, final HomeDataListener listener) {
        if (uid == null || uid.isEmpty()) {
            Log.w(TAG, "loadLastAccessedResource: Invalid UID provided.");
            // Tidak perlu kirim error, cukup kirim data null
            listener.onLastAccessedLoaded(null);
            return;
        }
        Log.d(TAG, "loadLastAccessedResource: Fetching last accessed resource for UID: " + uid);
        DocumentReference userRef = db.collection("users").document(uid);
        userRef.get().addOnSuccessListener(document -> {
            if (document.exists()) {
                // Ambil field 'lastAccessedResource' sebagai Map
                Object rawData = document.get("lastAccessedResource");
                if (rawData instanceof Map) {
                    Map<String, Object> lastAccessedData = (Map<String, Object>) rawData;
                    Log.d(TAG, "loadLastAccessedResource: Data lastAccessedResource ditemukan.");
                    listener.onLastAccessedLoaded(lastAccessedData); // Kirim Map ke listener
                } else {
                    Log.d(TAG, "loadLastAccessedResource: Field lastAccessedResource tidak ada, null, atau bukan Map.");
                    listener.onLastAccessedLoaded(null); // Kirim null jika field tidak ada/salah tipe
                }
            } else {
                Log.w(TAG, "loadLastAccessedResource: User document not found.");
                listener.onLastAccessedLoaded(null); // Kirim null jika user tidak ada
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "loadLastAccessedResource: Gagal mengambil lastAccessedResource: ", e);
            // Tidak perlu kirim error ke UI, cukup kirim data null
            listener.onLastAccessedLoaded(null);
        });
    }


    /**
     * Fungsi untuk logout.
     */
    public void logout() {
        mAuth.signOut();
        Log.i(TAG, "User logged out.");
    }
}

