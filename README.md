# 🎓 Study Gate (Aplikasi Belajar Mandiri)

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Firebase](https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Architecture](https://img.shields.io/badge/Architecture-MVC-blue?style=for-the-badge)
![Material Design](https://img.shields.io/badge/Design-Material_3-757575?style=for-the-badge&logo=materialdesign&logoColor=white)

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
* **Resource Browsing:** Jelajahi materi berdasarkan Program Studi (*Prodi*) dan Mata Kuliah menggunakan *Chip Filters*.
* **Smart Search:** Pencarian materi secara real-time (local filtering) berdasarkan judul atau mata kuliah.
* **Last Accessed History:** Fitur "Lanjutkan Belajar" yang otomatis menyimpan dan menampilkan materi terakhir yang dibuka oleh user.
* **Integrated WebView:** Membuka materi berbasis web (artikel/video) langsung di dalam aplikasi dengan dukungan **DOM Storage** untuk website modern.

#### 👨‍💻 Admin Features
* **Resource Management (CRUD):** Tambah, Edit, dan Hapus materi pembelajaran dengan mudah.
* **Real-time Updates:** Perubahan data materi langsung terlihat oleh user tanpa perlu refresh manual (Firestore Realtime Updates).
* **Metadata Management:** Dropdown dinamis untuk relasi antara Program Studi dan Mata Kuliah.

---

## 🛠️ Teknologi & Arsitektur

Project ini dibangun dengan standar industri modern menggunakan pola desain **MVC (Model-View-Controller)** untuk memastikan skalabilitas dan kemudahan maintenance.

### 1. Core & Build System
* **Language:** Java (Native Android)
* **Build Tool:** Gradle (Kotlin DSL / `.kts`)
* **Minimum SDK:** Android 24 (Nougat)
* **Target SDK:** Android 31 (Android 12)

### 2. Backend & Cloud Services (Firebase)
* **Authentication:** Manajemen sesi pengguna (Login/Register/Logout).
* **Cloud Firestore:** Database NoSQL untuk penyimpanan data users, resources, prodi, dan mata kuliah secara real-time.
* **Google Services Plugin:** Integrasi core layanan Google.

### 3. Android Jetpack & Libraries
* **ViewBinding:** Pengganti `findViewById` untuk interaksi UI yang lebih aman (Null Safety).
* **RecyclerView:** Menampilkan list data yang besar secara efisien.
* **Fragment Container:** Manajemen navigasi halaman (Home, Account, dll).
* **CoordinatorLayout & NestedScrollView:** Handling scrolling yang kompleks pada halaman detail.
* **Android WebKit:** Custom WebView Client untuk merender halaman web eksternal di dalam aplikasi.

### 4. User Interface (UI)
* **Material Design 3 (MDC):** Implementasi komponen desain modern Google.
  * *Components:* MaterialCardView, TextInputLayout, MaterialButton, ChipGroup, BottomNavigationView.
* **Custom Drawables:** Aset vektor XML kustom untuk ikonografi dan background.

### 5. Arsitektur MVC
* **Model:** Menangani logika bisnis dan komunikasi database (contoh: `AuthModel`, `HomeModel`, `AdminManageResourceModel`). Menggunakan **Interface Callbacks** untuk mengirim data kembali ke Controller secara asinkron.
* **View:** XML Layouts dan Activity/Fragment yang menampilkan data.
* **Controller:** Activity bertindak sebagai penghubung yang memanggil fungsi di Model dan memperbarui View.

---

## 📸 Screenshots

| Login Screen | User Dashboard | Resource Browse | Admin Panel |
|:---:|:---:|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/b80c27e7-6238-4b39-a0fa-22d23b61786d" width="200" /> | <img src="https://github.com/user-attachments/assets/edc42c9a-2213-4032-b230-fab3125fafc1" width="200" /> | <img src="https://github.com/user-attachments/assets/a40ba2e4-05dd-430e-9ee2-6ad9cc5b176c" width="200" /> | <img src="https://github.com/user-attachments/assets/b461a0fb-2727-4f25-acba-44a8fd9bd87c" width="200" /> |

---

## 🚀 Cara Instalasi (Local Development)

Ikuti langkah ini untuk menjalankan project di Android Studio:

1.  **Clone Repository**
    ```bash
    git clone [https://github.com/dinarfa/studygate_SI-C.git](https://github.com/dinarfa/studygate_SI-C.git)
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
