package com.sanalab.sijiusu.core.component;

import com.sanalab.sijiusu.auth.data.Role;
import com.sanalab.sijiusu.auth.database.repository.UserRepository;
import com.sanalab.sijiusu.siji_admin.database.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final HashEncoder hashEncoder;

    @Autowired
    public DataSeeder(
        UserRepository userRepository,
        HashEncoder hashEncoder
    ) {
        this.userRepository = userRepository;
        this.hashEncoder = hashEncoder;
    }

    @Override
    public void run(String... args) {
        if(userRepository.count() == 0) {
            this.seedAdmin();
        }
    }

    private void seedAdmin() {
        var admin = new Admin();
        admin.setName("Admin");
        admin.setEmail("admin@example.com");
        admin.setPasswordHashed(hashEncoder.encode("test"));
        admin.setRole(Role.Admin);
        admin.setNip("admin");

        userRepository.save(admin);
    }
}
