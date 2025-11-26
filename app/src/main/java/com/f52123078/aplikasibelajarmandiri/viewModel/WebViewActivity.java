package com.f52123078.aplikasibelajarmandiri.viewModel;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

// Pastikan import ini sesuai dengan lokasi file layout XML Anda
import com.f52123078.aplikasibelajarmandiri.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    // Menggunakan ViewBinding untuk menggantikan findViewById
    private ActivityWebViewBinding binding;

    // Konstanta key untuk pengiriman data via Intent
    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";

    @SuppressLint("SetJavaScriptEnabled") // Mengabaikan warning keamanan karena kita memang butuh JS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Inisialisasi ViewBinding (Menghubungkan kode Java dengan layout XML)
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 2. Mengambil data URL dan Judul yang dikirim dari Activity sebelumnya (ResourceAdapter)
        String url = getIntent().getStringExtra(EXTRA_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);

        // 3. Setup Toolbar (Bagian atas aplikasi)
        setSupportActionBar(binding.toolbarWebview);
        if (getSupportActionBar() != null) {
            // Set judul toolbar (gunakan judul dari intent, atau "Loading..." jika null)
            getSupportActionBar().setTitle(title != null ? title : "Loading...");
            // Menampilkan tombol panah kembali (back button) di toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Aksi ketika tombol panah kembali ditekan: tutup activity ini
        binding.toolbarWebview.setNavigationOnClickListener(v -> finish());

        // --- BAGIAN PENTING: PENGATURAN WEBVIEW ---
        WebSettings webSettings = binding.webView.getSettings();

        // A. Aktifkan JavaScript (Wajib untuk hampir semua web modern)
        webSettings.setJavaScriptEnabled(true);

        // B. SOLUSI MASALAH ANDA: Aktifkan DOM Storage
        // Website SPA (Single Page Application) seperti CodeDex butuh LocalStorage browser untuk jalan.
        // Tanpa ini, web modern seringkali blank/putih saja.
        webSettings.setDomStorageEnabled(true);

        // C. Opsional: Aktifkan Database Storage (Pelengkap DOM Storage)
        webSettings.setDatabaseEnabled(true);

        // 4. Setup WebViewClient
        // WebViewClient menangani navigasi dan event halaman (mulai loading, selesai loading)
        binding.webView.setWebViewClient(new WebViewClient() {

            // Dijalankan ketika halaman mulai dimuat
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Tampilkan ProgressBar agar user tahu proses sedang berjalan
                binding.progressBarWeb.setVisibility(View.VISIBLE);
            }

            // Dijalankan ketika halaman selesai dimuat sepenuhnya
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Sembunyikan ProgressBar
                binding.progressBarWeb.setVisibility(View.GONE);

                // Update judul toolbar dengan judul asli website jika dari Intent kosong
                if (title == null || title.isEmpty()) {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(view.getTitle());
                    }
                }
            }
        });

        // 5. Setup WebChromeClient
        // WebChromeClient menangani elemen UI browser seperti Progress loading (0-100%), Alert, dll.
        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // Logika: Jika progress belum 100%, pastikan ProgressBar terlihat
                if (newProgress < 100 && binding.progressBarWeb.getVisibility() == View.GONE) {
                    binding.progressBarWeb.setVisibility(View.VISIBLE);
                }
                // Jika sudah 100%, sembunyikan
                if (newProgress == 100) {
                    binding.progressBarWeb.setVisibility(View.GONE);
                }
            }
        });

        // 6. Eksekusi Muat URL
        // Cek agar tidak crash jika URL null
        if (url != null) {
            binding.webView.loadUrl(url);
        }
    }

    // 7. Handle Tombol Back Fisik di HP
    @Override
    public void onBackPressed() {
        // Jika WebView punya histori (user sudah klik link di dalam web), back akan kembali ke halaman web sebelumnya
        if (binding.webView.canGoBack()) {
            binding.webView.goBack();
        } else {
            // Jika tidak ada histori web, back akan menutup Activity (kembali ke aplikasi)
            super.onBackPressed();
        }
    }
}