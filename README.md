# 🎓 Study Gate (Aplikasi Belajar Mandiri)

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Firebase](https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Architecture](https://img.shields.io/badge/Architecture-MVC-blue?style=for-the-badge)

> **Your Gateway to Knowledge.** > Aplikasi manajemen sumber belajar mandiri berbasis Android yang menghubungkan mahasiswa dengan materi kuliah secara terstruktur, real-time, dan terpersonalisasi.

---

## 📱 Tentang Aplikasi

**Study Gate** adalah aplikasi *Mobile Learning Resource Manager* yang dibangun untuk memfasilitasi mahasiswa dalam mengakses materi perkuliahan (PDF, Video, Link Web) di luar kelas.

Aplikasi ini menggunakan sistem **Single Entry Point Authentication**, di mana Admin dan User (Mahasiswa) masuk melalui gerbang login yang sama, namun sistem secara cerdas mengarahkan mereka ke antarmuka yang berbeda berdasarkan hak akses (Role-Based Access Control).

### ✨ Fitur Unggulan

#### 🔐 Smart Authentication & Security
* **Single Sign-On Interface:** Satu halaman login untuk semua tipe pengguna.
* **Role-Based Redirection:** Sistem otomatis mendeteksi apakah user adalah `admin` atau `user` dan mengarahkan ke dashboard yang sesuai.
* **Secure Registration:** Validasi input ketat dan enkripsi standar Firebase Auth.

#### 👨‍🎓 User (Mahasiswa) Features
* **Resource Browsing:** Jelajahi materi berdasarkan Program Studi (*Prodi*) dan Mata Kuliah.
* **Smart Search:** Pencarian materi secara real-time dan filtering yang dinamis.
* **Last Accessed History:** Fitur "Lanjutkan Belajar" yang otomatis menyimpan dan menampilkan materi terakhir yang dibuka oleh user.
* **Integrated WebView:** Membuka materi berbasis web (artikel/video) langsung di dalam aplikasi tanpa keluar ke browser eksternal.

#### 👨‍💻 Admin Features
* **Resource Management (CRUD):** Tambah, Edit, dan Hapus materi pembelajaran dengan mudah.
* **Real-time Updates:** Perubahan data materi langsung terlihat oleh user tanpa perlu refresh manual.
* **Metadata Management:** Mengelola relasi antara Program Studi dan Mata Kuliah.

---

## 🛠️ Teknologi & Arsitektur

Project ini dibangun menggunakan pola desain **MVC (Model-View-Controller)** yang ketat untuk memastikan kode yang bersih, terorganisir, dan mudah dikembangkan.

### 1. Tech Stack
* **Bahasa:** Java (Native Android)
* **Database:** Cloud Firestore (NoSQL Realtime Database)
* **Auth:** Firebase Authentication
* **UI Components:** Material Design 3, RecyclerView, CardView

### 2. Struktur Data (Firestore)
Aplikasi ini menggunakan koleksi NoSQL yang saling berelasi:
* `users`: Menyimpan profil pengguna, role (`admin`/`user`), dan history `lastAccessedResource`.
* `resources`: Menyimpan metadata materi (Judul, Link, Tipe, ID Prodi, ID Matkul).
* `prodi`: Data Program Studi.
* `mata_kuliah`: Data Mata Kuliah yang terhubung ke Prodi.

### 3. Implementasi MVC
* **Model:** Menangani logika bisnis dan komunikasi database (contoh: `AuthModel`, `HomeModel`, `AdminManageResourceModel`). Menggunakan **Interface Callbacks** untuk mengirim data kembali ke Controller secara asinkron.
* **View:** XML Layouts dan Activity/Fragment yang menampilkan data.
* **Controller:** Activity bertindak sebagai penghubung yang memanggil fungsi di Model dan memperbarui View.

---

## 📸 Screenshots

| Login Screen | User Dashboard | Resource Browse | Admin Panel |
|:---:|:---:|:---:|:---:|
| *(Tempel Screenshot Login)* | *(Tempel Screenshot Home)* | *(Tempel Screenshot Browse)* | *(Tempel Screenshot CRUD)* |

---

## 🚀 Cara Instalasi (Local Development)

Ikuti langkah ini untuk menjalankan project di Android Studio:

1.  **Clone Repository**
    ```bash
    git clone [https://github.com/username-anda/study-gate.git](https://github.com/username-anda/study-gate.git)
    ```
2.  **Buka di Android Studio**
    * File > Open > Pilih folder project.
3.  **Konfigurasi Firebase**
    * Buat project baru di [Firebase Console](https://console.firebase.google.com/).
    * Aktifkan **Authentication** (Email/Password).
    * Aktifkan **Firestore Database**.
    * Unduh file `google-services.json` dan letakkan di dalam folder `app/`.
4.  **Sync Gradle & Run**
    * Tunggu proses Gradle selesai, lalu tekan tombol **Run (▶)**.

---

## 👥 Tim Pengembang

Project ini dikerjakan oleh **Kelompok 1 (Kelas C)** sebagai bagian dari tugas Mata Kuliah Pemrograman Mobile.

| No | Nama Mahasiswa | NIM |
|:--:|:---|:---|
| 1 | **Dinar Fauziah** | F52123078 |
| 2 | **Muh. Azhar Rasyid** | F52123089 |
| 3 | **Kadek Surya** | F52123099 |
| 4 | **Arya Yudistira Syaifrul** | F52123079 |

---

<center>Made with ❤️ by Group 1 (Class C) for Better Education</center>
