package com.f52123078.aplikasibelajarmandiri.model;
import java.util.Date;
import com.google.firebase.firestore.ServerTimestamp;

public class Resource {
    // --- Fields ---
    private String title;
    private String desc;
    private String course;
    private String category;
    private String type;
    private String url;
    private String prodiId;
    private String mkId;
    private Date createdAt;

    // Konstruktor kosong wajib untuk Firestore
    public Resource() {}

    // --- Getters ---
    public String getTitle() { return title; }
    public String getDesc() { return desc; }
    public String getCourse() { return course; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getUrl() { return url; }
    public String getProdiId() { return prodiId; }
    public String getMkId() { return mkId; }

    // --- PERBAIKAN 4: Pastikan @ServerTimestamp ada di getter ---
    @ServerTimestamp
    public Date getCreatedAt() { return createdAt; }


    // --- Setters ---
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setProdiId(String prodiId) {
        this.prodiId = prodiId;
    }
    public void setMkId(String mkId) {
        this.mkId = mkId;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

