package com.f52123078.aplikasibelajarmandiri.viewModel;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.f52123078.aplikasibelajarmandiri.databinding.ActivityRegisterBinding;
import com.f52123078.aplikasibelajarmandiri.model.AuthModel;

public class RegisterActivity extends AppCompatActivity implements AuthModel.RegisterListener {

    // View: Dikelola oleh ViewBinding
    private ActivityRegisterBinding binding;

    // Model: Kelas logika kita
    private AuthModel authModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Controller: Menginisialisasi View
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Controller: Menginisialisasi Model
        authModel = new AuthModel();

        // Controller: Menangani Aksi Pengguna (User Input)
        setupClickListeners();
    }

    private void setupClickListeners() {
        // Tombol kembali di Toolbar
        binding.toolbar.setNavigationOnClickListener(v -> {
            finish(); // Tutup activity ini dan kembali ke Login
        });

        // Link "Sudah punya akun?"
        binding.tvGoToLogin.setOnClickListener(v -> {
            finish(); // Tutup activity ini dan kembali ke Login
        });

        // Tombol Register
        binding.btnRegister.setOnClickListener(v -> {
            handleRegister();
        });
    }

    private void handleRegister() {
        // Ambil data dari View
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPassword = binding.etConfirmPassword.getText().toString().trim();

        // Bersihkan error HCI sebelumnya
        binding.tilName.setError(null);
        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);
        binding.tilConfirmPassword.setError(null);

        // Controller: Update View (beri feedback loading)
        showLoading(true);

        // Controller: Memanggil Model untuk memproses data
        authModel.registerUser(name, email, password, confirmPassword, this);
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.btnRegister.setEnabled(false);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnRegister.setEnabled(true);
        }
    }

    // --- Hasil dari Model (Callback) ---

    @Override
    public void onRegisterSuccess() {
        showLoading(false);
        Toast.makeText(this, "Registrasi Berhasil! Silakan Login.", Toast.LENGTH_LONG).show();
        finish(); // Tutup halaman Register dan kembali ke Login
    }

    @Override
    public void onRegisterFailure(String error) {
        showLoading(false);

        // Prinsip HCI: Tampilkan error di field yang relevan
        if (error.contains("password") || error.contains("Password") || error.contains("minimal 6 karakter")) {
            binding.tilPassword.setError(error);
            binding.tilPassword.requestFocus();
        } else if (error.contains("Konfirmasi")) {
            binding.tilConfirmPassword.setError(error);
            binding.tilConfirmPassword.requestFocus();
        } else if (error.contains("Email") || error.contains("email")) {
            binding.tilEmail.setError(error);
            binding.tilEmail.requestFocus();
        } else if (error.contains("field")) {
            binding.tilName.setError(error);
            binding.tilName.requestFocus();
        } else {
            // Error umum
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }
}
