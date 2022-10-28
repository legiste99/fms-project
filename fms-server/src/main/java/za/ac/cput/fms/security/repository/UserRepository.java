package za.ac.cput.fms.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.fms.security.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
    User getUserByEmail(String email);
}