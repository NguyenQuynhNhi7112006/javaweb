package an_paper.repository;

import an_paper.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Tương tự, đã có sẵn các tính năng CRUD cơ bản
}