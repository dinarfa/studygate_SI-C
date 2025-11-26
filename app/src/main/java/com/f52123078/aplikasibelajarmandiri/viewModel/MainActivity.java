package com.f52123078.aplikasibelajarmandiri.viewModel;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.f52123078.aplikasibelajarmandiri.databinding.ActivityMainBinding; // Import ViewBinding
import com.f52123078.aplikasibelajarmandiri.model.AuthModel;
import com.f52123078.aplikasibelajarmandiri.viewModel.HomeActivity;

public class MainActivity extends AppCompatActivity implements AuthModel.LoginListener {

    // View: Dikelola oleh ViewBinding
    private ActivityMainBinding binding;

    // Model: Kelas logika kita
    private AuthModel authModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authModel = new AuthModel();

        binding.btnLogin.setOnClickListener(v -> {
            handleLogin();
        });

        // --- PERUBAHAN DI SINI ---
        binding.tvGoToRegister.setOnClickListener(v -> {
            // Arahkan ke RegisterActivity
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            // Toast.makeText(this, "Nanti kita ke halaman Register", Toast.LENGTH_SHORT).show(); // Hapus ini
        });
    }

    private void handleLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        // Controller: Update View (beri feedback loading)
        showLoading(true);

        // Controller: Memanggil Model untuk memproses data
        // 'this' (yaitu MainActivity) dikirim sebagai listener
        authModel.loginUser(email, password, this);
    }

    /**
     * Controller: Mengatur tampilan feedback loading (prinsip HCI)
     */
    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.btnLogin.setEnabled(false);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnLogin.setEnabled(true);
        }
    }

    // --- Hasil dari Model (Callback) ---

    @Override
    public void onLoginSuccess(String role) {
        showLoading(false);
        Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show();

        if (role.equals("admin")) {
            // Nanti kita buat AdminDashboardActivity.java
             Intent intent = new Intent(MainActivity.this, AdminDashboardActivity.class);
             startActivity(intent);
        } else {
            // --- PERUBAHAN DI SINI ---
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        finish(); // Tutup halaman login setelah berhasil
    }

    @Override
    public void onLoginFailure(String error) {
        // Controller: Update View (hentikan loading dan tampilkan error)
        showLoading(false);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();


        if (error.contains("Password")) {
            binding.tilPassword.setError(error);
        } else {
            binding.tilEmail.setError(error);
        }
    }
}