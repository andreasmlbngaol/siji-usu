package com.sanalab.sijiusu.siji_admin.database.model;

import com.sanalab.sijiusu.auth.database.model.User;
import jakarta.persistence.*;

@SuppressWarnings("unused")
@Entity(name = "admins")
public class Admin extends User {
    @Column(nullable = false, unique = true)
    private String nip;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}
