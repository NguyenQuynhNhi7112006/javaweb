package an_paper.repository;

import an_paper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm kiếm user dựa vào username (dùng cho tính năng đăng nhập)
    Optional<User> findByUsername(String username);
}