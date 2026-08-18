package an_paper.repository;

import an_paper.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    // Kiểm tra xem User đã từng mua sản phẩm này và đã thanh toán/nhận hàng chưa
    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.orderDetails od " +
           "WHERE o.user.id = :userId AND od.product.id = :productId AND o.status IN ('RECEIVED', 'PAID')")
    boolean hasUserPurchasedProduct(@Param("userId") Long userId, @Param("productId") Long productId);

    // Chỉ tính tổng doanh thu từ các đơn hàng có trạng thái PAID
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'PAID'")
    BigDecimal calculateTotalRevenue();
}
