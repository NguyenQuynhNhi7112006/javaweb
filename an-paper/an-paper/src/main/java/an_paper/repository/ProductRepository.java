package an_paper.repository;

import an_paper.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Kế thừa JpaRepository là đã có sẵn các hàm: findAll(), save(), findById(), deleteById()...
}