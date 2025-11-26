package com.f52123078.aplikasibelajarmandiri.model;

// Hasil dari operasi Login di Repository
public abstract class LoginResult {

    // Sukses, membawa data role
    public static final class Success extends LoginResult {
        private final String role;

        public Success(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }

    // Error
    public static final class Error extends LoginResult {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}