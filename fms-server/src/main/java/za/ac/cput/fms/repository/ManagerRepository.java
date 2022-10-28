package za.ac.cput.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.fms.domain.personal.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {

}
