package an_paper.repository;

import an_paper.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Tìm kiếm quyền theo tên (Ví dụ: "ADMIN")
    Optional<Role> findByName(String name);
}