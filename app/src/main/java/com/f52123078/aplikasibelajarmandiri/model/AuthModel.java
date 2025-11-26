package com.f52123078.aplikasibelajarmandiri.model;

import android.util.Log; // <-- WAJIB TAMBAHKAN IMPORT INI

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AuthModel {

    // --- TAMBAHKAN TAG INI UNTUK LOGCAT ---
    private static final String TAG = "AuthModel";

    // Interface Login
    public interface LoginListener {
        void onLoginSuccess(String role); // Mengirim role (admin/user)
        void onLoginFailure(String error);
    }

    // Interface Register
    public interface RegisterListener {
        void onRegisterSuccess();
        void onRegisterFailure(String error);
    }

    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;

    public AuthModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Fungsi utama Model: Melakukan login dan mengecek role
     */
    public void loginUser(String email, String password, final LoginListener listener) {
        if (email.isEmpty() || password.isEmpty()) {
            listener.onLoginFailure("Email dan Password tidak boleh kosong.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && mAuth.getCurrentUser() != null) {
                        // Jika login berhasil, cek role di Firestore
                        checkUserRole(mAuth.getCurrentUser().getUid(), listener);
                    } else {
                        // --- PERBAIKAN LOGGING ---
                        String error = task.getException() != null ? task.getException().getMessage() : "Email atau Password salah";
                        Log.e(TAG, "Login Gagal: " + error); // Cetak error ke Logcat
                        listener.onLoginFailure(error);
                    }
                });
    }

    /**
     * Helper function untuk mengambil data role dari koleksi 'users'
     */
    private void checkUserRole(String uid, final LoginListener listener) {
        DocumentReference userRef = db.collection("users").document(uid);

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Data user ditemukan, ambil role-nya
                    String role = document.getString("role");
                    if (role != null) {
                        listener.onLoginSuccess(role);
                    } else {
                        listener.onLoginFailure("Data role tidak ditemukan.");
                    }
                } else {
                    // Ini kasus aneh, user ada di Auth tapi tidak ada di Firestore
                    listener.onLoginFailure("Detail data user tidak ditemukan.");
                }
            } else {
                // --- PERBAIKAN LOGGING ---
                String error = task.getException() != null ? task.getException().getMessage() : "Gagal memverifikasi role";
                Log.e(TAG, "Gagal Cek Role: " + error); // Cetak error ke Logcat
                listener.onLoginFailure(error);
            }
        });
    }
    /**
     * Fungsi Model: Registrasi User Baru
     */
    public void registerUser(String name, String email, String password, String confirmPassword, final RegisterListener listener) {
        // 1. Validasi Input (Controller)
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            listener.onRegisterFailure("Semua field tidak boleh kosong.");
            return;
        }
        if (password.length() < 6) {
            listener.onRegisterFailure("Password minimal 6 karakter.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            listener.onRegisterFailure("Konfirmasi password tidak cocok.");
            return;
        }

        // 2. Buat User di Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && mAuth.getCurrentUser() != null) {
                        // 3. Jika sukses, buat dokumen user di Firestore
                        String uid = mAuth.getCurrentUser().getUid();
                        createUserDocument(uid, name, email, listener);
                    } else {
                        // --- PERBAIKAN LOGGING ---
                        String error = task.getException() != null ? task.getException().getMessage() : "Registrasi Gagal";
                        Log.e(TAG, "Gagal Registrasi (Auth): " + error); // Cetak error ke Logcat
                        listener.onRegisterFailure(error);
                        }
                });
    }

    /**
     * Helper untuk membuat dokumen user di koleksi 'users'
     */
    private void createUserDocument(String uid, String name, String email, final RegisterListener listener) {
        DocumentReference userRef = db.collection("users").document(uid);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("email", email);
        userMap.put("role", "user"); // Default role adalah 'user'

        userRef.set(userMap)
                .addOnSuccessListener(aVoid -> {
                    // Sukses membuat dokumen
                    listener.onRegisterSuccess();
                })
                .addOnFailureListener(e -> {
                    // --- PERBAIKAN LOGGING ---
                    String error = e.getMessage() != null ? e.getMessage() : "Registrasi Gagal (Database)";
                    Log.e(TAG, "Gagal Registrasi (Firestore): " + error); // Cetak error ke Logcat
                    listener.onRegisterFailure(error);
                });
    }
}
