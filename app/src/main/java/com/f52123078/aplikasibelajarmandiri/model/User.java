package com.f52123078.aplikasibelajarmandiri.model;

// Ini adalah kelas Model (POJO) untuk memetakan data dari Firestore
public class User {
    private String name;
    private String email;
    private String role;

    // Diperlukan constructor kosong untuk deserialisasi Firestore
    public User() {}

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getter (juga diperlukan oleh Firestore)
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // Setter (Opsional, tapi praktik yang baik)
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
