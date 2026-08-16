package lk.ijse.eca.memberservice.seeder;

import lk.ijse.eca.memberservice.entity.Role;
import lk.ijse.eca.memberservice.entity.User;
import lk.ijse.eca.memberservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.default.email:admin@fitbuddy.com}")
    private String defaultAdminEmail;

    @Value("${admin.default.password:Admin@123!}")
    private String defaultAdminPassword;

    @Override
    public void run(String... args) throws Exception {
        log.info("Checking if default ADMIN user exists...");
        
        if (userRepository.existsByRole(Role.ADMIN)) {
            log.info("ADMIN user already exists. Skipping database seeding.");
            return;
        }

        log.info("No ADMIN user found. Seeding default ADMIN user...");

        User adminUser = new User();
        adminUser.setFirstName("System");
        adminUser.setLastName("Admin");
        adminUser.setEmail(defaultAdminEmail);
        adminUser.setPassword(passwordEncoder.encode(defaultAdminPassword));
        adminUser.setRole(Role.ADMIN);
        adminUser.setCreatedAt(LocalDateTime.now());
        adminUser.setUpdatedAt(LocalDateTime.now());

        userRepository.save(adminUser);
        log.info("Default ADMIN user seeded successfully with email: {}", defaultAdminEmail);
    }
}
